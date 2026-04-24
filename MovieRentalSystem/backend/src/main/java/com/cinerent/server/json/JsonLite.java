package com.cinerent.server.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Tiny JSON helper (no dependencies).
 * Supports: objects, arrays, strings, numbers, booleans, null.
 */
public final class JsonLite {
  private JsonLite() {}

  public static String stringify(Object v) {
    StringBuilder sb = new StringBuilder();
    writeValue(sb, v);
    return sb.toString();
  }

  public static Map<String, Object> parseObject(String json) {
    Object v = new Parser(json).parseValue();
    if (!(v instanceof Map)) throw new IllegalArgumentException("Expected JSON object");
    //noinspection unchecked
    return (Map<String, Object>) v;
  }

  public static Object parse(String json) {
    return new Parser(json).parseValue();
  }

  private static void writeValue(StringBuilder sb, Object v) {
    if (v == null) {
      sb.append("null");
    } else if (v instanceof String s) {
      sb.append('"').append(escape(s)).append('"');
    } else if (v instanceof Number n) {
      sb.append(n);
    } else if (v instanceof Boolean b) {
      sb.append(b ? "true" : "false");
    } else if (v instanceof Map<?, ?> m) {
      sb.append('{');
      boolean first = true;
      for (Map.Entry<?, ?> e : m.entrySet()) {
        if (!(e.getKey() instanceof String)) continue;
        if (!first) sb.append(',');
        first = false;
        sb.append('"').append(escape((String) e.getKey())).append('"').append(':');
        writeValue(sb, e.getValue());
      }
      sb.append('}');
    } else if (v instanceof List<?> list) {
      sb.append('[');
      for (int i = 0; i < list.size(); i++) {
        if (i > 0) sb.append(',');
        writeValue(sb, list.get(i));
      }
      sb.append(']');
    } else {
      sb.append('"').append(escape(String.valueOf(v))).append('"');
    }
  }

  private static String escape(String s) {
    StringBuilder out = new StringBuilder(s.length() + 16);
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '"' -> out.append("\\\"");
        case '\\' -> out.append("\\\\");
        case '\b' -> out.append("\\b");
        case '\f' -> out.append("\\f");
        case '\n' -> out.append("\\n");
        case '\r' -> out.append("\\r");
        case '\t' -> out.append("\\t");
        default -> {
          if (c < 0x20) out.append(String.format("\\u%04x", (int) c));
          else out.append(c);
        }
      }
    }
    return out.toString();
  }

  private static final class Parser {
    private final String s;
    private int i = 0;

    Parser(String s) {
      this.s = s == null ? "" : s;
    }

    Object parseValue() {
      skipWs();
      if (i >= s.length()) return null;
      char c = s.charAt(i);
      return switch (c) {
        case '{' -> parseObject();
        case '[' -> parseArray();
        case '"' -> parseString();
        case 't' -> { expect("true"); yield Boolean.TRUE; }
        case 'f' -> { expect("false"); yield Boolean.FALSE; }
        case 'n' -> { expect("null"); yield null; }
        default -> parseNumber();
      };
    }

    private Map<String, Object> parseObject() {
      expect('{');
      skipWs();
      Map<String, Object> m = new LinkedHashMap<>();
      if (peek('}')) { i++; return m; }
      while (true) {
        skipWs();
        String key = parseString();
        skipWs();
        expect(':');
        Object val = parseValue();
        m.put(key, val);
        skipWs();
        if (peek('}')) { i++; break; }
        expect(',');
      }
      return m;
    }

    private List<Object> parseArray() {
      expect('[');
      skipWs();
      List<Object> out = new ArrayList<>();
      if (peek(']')) { i++; return out; }
      while (true) {
        Object v = parseValue();
        out.add(v);
        skipWs();
        if (peek(']')) { i++; break; }
        expect(',');
      }
      return out;
    }

    private String parseString() {
      expect('"');
      StringBuilder out = new StringBuilder();
      while (i < s.length()) {
        char c = s.charAt(i++);
        if (c == '"') break;
        if (c == '\\') {
          if (i >= s.length()) break;
          char esc = s.charAt(i++);
          switch (esc) {
            case '"', '\\', '/' -> out.append(esc);
            case 'b' -> out.append('\b');
            case 'f' -> out.append('\f');
            case 'n' -> out.append('\n');
            case 'r' -> out.append('\r');
            case 't' -> out.append('\t');
            case 'u' -> {
              String hex = s.substring(i, Math.min(i + 4, s.length()));
              if (hex.length() == 4) {
                out.append((char) Integer.parseInt(hex, 16));
                i += 4;
              }
            }
            default -> out.append(esc);
          }
        } else {
          out.append(c);
        }
      }
      return out.toString();
    }

    private Number parseNumber() {
      int start = i;
      while (i < s.length()) {
        char c = s.charAt(i);
        if ((c >= '0' && c <= '9') || c == '-' || c == '+' || c == '.' || c == 'e' || c == 'E') {
          i++;
        } else break;
      }
      String num = s.substring(start, i).trim();
      if (num.isEmpty()) return 0;
      if (num.contains(".") || num.contains("e") || num.contains("E")) return Double.parseDouble(num);
      try {
        return Integer.parseInt(num);
      } catch (NumberFormatException e) {
        return Long.parseLong(num);
      }
    }

    private void skipWs() {
      while (i < s.length()) {
        char c = s.charAt(i);
        if (c == ' ' || c == '\n' || c == '\r' || c == '\t') i++;
        else break;
      }
    }

    private boolean peek(char c) {
      skipWs();
      return i < s.length() && s.charAt(i) == c;
    }

    private void expect(char c) {
      skipWs();
      if (i >= s.length() || s.charAt(i) != c) {
        throw new IllegalArgumentException("Expected '" + c + "' at position " + i);
      }
      i++;
    }

    private void expect(String lit) {
      skipWs();
      if (!s.regionMatches(i, lit, 0, lit.length())) {
        throw new IllegalArgumentException("Expected \"" + lit + "\" at position " + i);
      }
      i += lit.length();
    }
  }
}

