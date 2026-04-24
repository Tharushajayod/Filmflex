/* Simple HTML partial includes for multi-page static site.
   Note: requires serving over http(s) (e.g. Live Server). */
"use strict";

async function includePartials() {
  const nodes = document.querySelectorAll("[data-include]");
  await Promise.all(
    [...nodes].map(async (el) => {
      const url = el.getAttribute("data-include");
      if (!url) return;
      const res = await fetch(url, { cache: "no-cache" });
      if (!res.ok) throw new Error(`Failed to load include: ${url}`);
      el.outerHTML = await res.text();
    }),
  );
}

function setActiveNav() {
  const page = document.body.getAttribute("data-page");
  if (!page) return;
  document.querySelectorAll(".nav-link").forEach((a) => {
    a.classList.toggle("active", a.getAttribute("data-nav") === page);
  });
}

function setYear() {
  const y = document.getElementById("cr-year");
  if (y) y.textContent = String(new Date().getFullYear());
}

document.addEventListener("DOMContentLoaded", async () => {
  try {
    await includePartials();
  } catch (e) {
    // If opened via file://, fetch may be blocked; fail gracefully.
    console.warn(e);
  }
  setActiveNav();
  setYear();
});

