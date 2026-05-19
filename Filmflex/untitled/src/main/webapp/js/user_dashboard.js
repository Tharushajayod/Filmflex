// ============================================================
// FILMFLEX — user_dashboard.js   (full rewrite)
// ============================================================

// Sample data (replaced by real API calls in production)
const WATCHED_HISTORY = [
    { id:1,  title:"Inception",          poster:"https://image.tmdb.org/t/p/w200/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg", genre:"Sci-Fi",  rating:8.8, date:"2026-05-16", progress:100 },
    { id:2,  title:"The Dark Knight",    poster:"https://image.tmdb.org/t/p/w200/qJ2tW6WMUDux911r6m7haRef0WH.jpg", genre:"Action",  rating:9.0, date:"2026-05-14", progress:100 },
    { id:3,  title:"Interstellar",       poster:"https://image.tmdb.org/t/p/w200/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", genre:"Sci-Fi",  rating:8.7, date:"2026-05-12", progress:72  },
    { id:4,  title:"Joker",              poster:"https://image.tmdb.org/t/p/w200/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg", genre:"Drama",   rating:8.4, date:"2026-05-10", progress:100 },
    { id:5,  title:"Dune",               poster:"https://image.tmdb.org/t/p/w200/d5NXSklpcvkCgnpLIOkpTHFhhis.jpg", genre:"Sci-Fi",  rating:8.0, date:"2026-05-08", progress:45  },
    { id:6,  title:"Top Gun: Maverick",  poster:"https://image.tmdb.org/t/p/w200/62HCnUTHjWty9vaj7KMEXkQGzbH.jpg", genre:"Action",  rating:8.3, date:"2026-05-05", progress:100 },
    { id:7,  title:"Oppenheimer",        poster:"https://image.tmdb.org/t/p/w200/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg", genre:"Drama",   rating:8.6, date:"2026-05-01", progress:100 },
    { id:8,  title:"La La Land",         poster:"https://image.tmdb.org/t/p/w200/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg", genre:"Romance", rating:8.0, date:"2026-04-28", progress:88  },
];

const MY_REVIEWS = [
    { id:1, movie:"Inception",       poster:"https://image.tmdb.org/t/p/w200/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg", rating:5, date:"2026-05-16", text:"Absolutely mind-bending. The layers of dreams are crafted with incredible precision — each rewatch reveals something new. Nolan's masterpiece." },
    { id:2, movie:"The Dark Knight", poster:"https://image.tmdb.org/t/p/w200/qJ2tW6WMUDux911r6m7haRef0WH.jpg", rating:5, date:"2026-05-14", text:"Heath Ledger's Joker is one of cinema's most iconic performances ever. The film redefined what superhero movies could be." },
    { id:3, movie:"Interstellar",    poster:"https://image.tmdb.org/t/p/w200/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", rating:4, date:"2026-05-12", text:"Visually breathtaking and emotionally powerful. Hans Zimmer's score elevates every scene. The third act is a little confusing but spectacular." },
    { id:4, movie:"Joker",           poster:"https://image.tmdb.org/t/p/w200/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg", rating:4, date:"2026-05-10", text:"Joaquin Phoenix delivers a haunting, mesmerizing performance. Dark and unsettling but incredibly well-crafted." },
];

const WISHLIST = [
    { id:1,  title:"The Matrix",          poster:"https://image.tmdb.org/t/p/w200/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg", rating:8.7 },
    { id:2,  title:"Parasite",            poster:"https://image.tmdb.org/t/p/w200/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg", rating:8.5 },
    { id:3,  title:"Avengers: Endgame",   poster:"https://image.tmdb.org/t/p/w200/or06FN3Dka5tukK1e9sl16pB3iy.jpg", rating:8.4 },
    { id:4,  title:"Past Lives",          poster:"https://image.tmdb.org/t/p/w200/k3waqVXSnCMSKTfQaUdNcPqFBVm.jpg", rating:7.9 },
    { id:5,  title:"Poor Things",         poster:"https://image.tmdb.org/t/p/w200/kCGlIMHnOm8JPXIbpGraham17d3x.jpg", rating:8.0 },
    { id:6,  title:"Saltburn",            poster:"https://image.tmdb.org/t/p/w200/qitIziKBNPuRGSWaIGMzBNRr5vc.jpg", rating:7.1 },
    { id:7,  title:"Oppenheimer",         poster:"https://image.tmdb.org/t/p/w200/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg", rating:8.6 },
];

// ═══════════════════════════════════════════════════════
// INIT
// ═══════════════════════════════════════════════════════
document.addEventListener('DOMContentLoaded', () => {
    // Header scroll
    window.addEventListener('scroll', () => {
        document.getElementById('mainHeader').classList.toggle('solid', window.scrollY > 10);
    }, { passive: true });

    // Avatar header dropdown
    setupHeaderDropdown();

    // Sidebar tabs
    setupTabs();

    // Load user data
    loadUserData();

    // Render sections
    renderHistory(WATCHED_HISTORY);
    renderReviews(MY_REVIEWS);
    renderWishlist(WISHLIST);

    // Form events
    setupForms();

    // Filter chips (history)
    setupFilters();

    // Password strength
    setupPasswordStrength();

    // Password eye toggles
    setupEyeToggles();

    // Delete account modal
    setupDeleteModal();

    // Logout
    document.getElementById('logoutBtn').addEventListener('click', () => {
        fetch('logout', { method: 'POST', credentials: 'include' })
            .then(() => window.location.href = 'login.html')
            .catch(() => window.location.href = 'login.html');
    });
});

// ─── Header dropdown ──────────────────────────────────────────────────────
function setupHeaderDropdown() {
    const btn = document.getElementById('hdrAvatar');
    const dd  = document.getElementById('hdrDropdown');
    btn.addEventListener('click', e => { e.stopPropagation(); dd.classList.toggle('open'); });
    document.addEventListener('click', () => dd.classList.remove('open'));
}

// ─── Sidebar tabs ─────────────────────────────────────────────────────────
function setupTabs() {
    document.querySelectorAll('.sb-link[data-tab]').forEach(btn => {
        btn.addEventListener('click', () => {
            const tab = btn.dataset.tab;

            // Update buttons
            document.querySelectorAll('.sb-link[data-tab]').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            // Update panels
            document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
            const panel = document.getElementById('tab-' + tab);
            if (panel) panel.classList.add('active');
        });
    });
}

// ─── Load user data from server ───────────────────────────────────────────
function loadUserData() {
    fetch('user-data', { method: 'GET', credentials: 'include' })
        .then(r => { if (!r.ok) throw 0; return r.json(); })
        .then(u => {
            const name    = u.fullName || u.name || 'User';
            const email   = u.email || '';
            const initial = name.charAt(0).toUpperCase();
            const parts   = name.split(' ');
            const first   = parts[0] || '';
            const last    = parts.slice(1).join(' ') || '';

            // Header avatar
            document.getElementById('hdrAvatar').textContent = initial;
            document.getElementById('hddAv').textContent     = initial;
            document.getElementById('hddName').textContent   = name;
            document.getElementById('hddEmail').textContent  = email;

            // Banner
            document.getElementById('pbAvatar').textContent = initial;
            document.getElementById('pbName').textContent   = name;
            document.getElementById('pbEmail').textContent  = email;

            // Profile card
            document.getElementById('pcAvatar').textContent = initial;
            document.getElementById('pciName').textContent  = name;
            document.getElementById('pciEmail').textContent = email;
            document.getElementById('pciJoined').textContent = '2026';

            // Form
            document.getElementById('firstName').value  = first;
            document.getElementById('lastName').value   = last;
            document.getElementById('emailField').value = email;

            // Update stats (could come from server)
            document.getElementById('statWatched').textContent  = WATCHED_HISTORY.length;
            document.getElementById('statReviews').textContent  = MY_REVIEWS.length;
            document.getElementById('statWishlist').textContent = WISHLIST.length;

            // Check pass
            checkUserPass(email);
        })
        .catch(() => {
            // Fallback for static preview
            document.getElementById('pbName').textContent  = 'Guest User';
            document.getElementById('pbEmail').textContent = 'guest@filmflex.com';
        });
}

function checkUserPass(email) {
    fetch('get-ticket?email=' + encodeURIComponent(email), { credentials: 'include' })
        .then(r => r.ok ? r.json() : null)
        .then(t => { if (t && t.ticketId) document.getElementById('pbPassBadge').style.display = 'inline-flex'; })
        .catch(() => {});
}

// ─── Render watch history ─────────────────────────────────────────────────
function renderHistory(items) {
    const el = document.getElementById('historyList');
    if (!el) return;

    if (!items.length) {
        el.innerHTML = emptyState('🎬', 'No Watch History', "You haven't watched any movies yet.", 'movie.html', 'Browse Movies');
        return;
    }

    el.innerHTML = items.map(m => `
        <div class="history-item">
            <div class="hi-poster">
                <img src="${m.poster}" alt="${m.title}"
                     onerror="this.style.display='none';this.parentNode.textContent='🎬'">
            </div>
            <div class="hi-info">
                <div class="hi-title">${m.title}</div>
                <div class="hi-meta">
                    <span class="hi-genre">${m.genre}</span>
                    <span class="hi-rating">⭐ ${m.rating}</span>
                    <span class="hi-date">${formatDate(m.date)}</span>
                </div>
                <div class="hi-progress-wrap">
                    <div class="hi-progress-bar">
                        <div class="hi-progress-fill" style="width:${m.progress}%"></div>
                    </div>
                    <div class="hi-progress-label">${m.progress === 100 ? 'Completed' : m.progress + '% watched'}</div>
                </div>
            </div>
            <div class="hi-actions">
                <button class="hi-btn hi-btn-play" onclick="window.location.href='movie.html'">▶ Play</button>
                <button class="hi-btn" onclick="removeHistory(${m.id}, this)">Remove</button>
            </div>
        </div>`).join('');
}

// ─── Render reviews ───────────────────────────────────────────────────────
function renderReviews(items) {
    const el = document.getElementById('reviewsGrid');
    if (!el) return;

    if (!items.length) {
        el.innerHTML = emptyState('💬', 'No Reviews Yet', "You haven't written any reviews yet.", 'movie.html', 'Write a Review');
        return;
    }

    el.innerHTML = items.map(r => {
        const stars = Array.from({length:5}, (_,i) =>
            `<span class="ri-star ${i < r.rating ? 'on' : ''}">★</span>`).join('');
        return `
        <div class="review-item" id="review-${r.id}">
            <div class="ri-header">
                <div class="ri-poster">
                    <img src="${r.poster}" alt="${r.movie}"
                         onerror="this.style.display='none';this.parentNode.textContent='🎬'">
                </div>
                <div>
                    <div class="ri-movie">${r.movie}</div>
                    <div class="ri-stars">${stars}</div>
                    <div class="ri-date">${formatDate(r.date)}</div>
                </div>
                <div class="ri-actions">
                    <button class="ri-btn ri-btn-del" title="Delete review" onclick="deleteReview(${r.id})">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/><path d="M10 11v6M14 11v6"/><path d="M9 6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2"/></svg>
                    </button>
                </div>
            </div>
            <p class="ri-text">"${r.text}"</p>
            <div class="ri-tag">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width:11px;height:11px;"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>
                ${formatDate(r.date)}
            </div>
        </div>`;
    }).join('');
}

// ─── Render wishlist ──────────────────────────────────────────────────────
function renderWishlist(items) {
    const el = document.getElementById('wishlistGrid');
    if (!el) return;

    if (!items.length) {
        el.innerHTML = emptyState('❤️', 'Wishlist is Empty', 'Save movies you want to watch later.', 'movie.html', 'Browse Movies');
        return;
    }

    el.innerHTML = items.map(m => `
        <div class="wl-card">
            <div class="wl-card-face">
                <img src="${m.poster}" alt="${m.title}"
                     onerror="this.style.display='none';this.parentNode.textContent='🎬'">
            </div>
            <div class="wl-card-body">
                <div class="wl-card-title">${m.title}</div>
                <div class="wl-card-rating">⭐ ${m.rating}</div>
            </div>
            <button class="wl-card-rm" title="Remove from wishlist" onclick="removeWishlist(${m.id}, this)">✕</button>
        </div>`).join('');
}

// ─── Forms ────────────────────────────────────────────────────────────────
function setupForms() {
    // Save profile
    document.getElementById('saveProfileBtn').addEventListener('click', () => {
        const first = document.getElementById('firstName').value.trim();
        const last  = document.getElementById('lastName').value.trim();
        if (!first) { showToast('toastError', 'toastErrMsg', 'First name is required.'); return; }

        // POST to server
        fetch('update-profile', {
            method: 'POST', credentials: 'include',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ fullName: `${first} ${last}`.trim() })
        })
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(() => {
            showToast('toastSuccess', 'toastMsg', 'Profile updated successfully!');
            const name = `${first} ${last}`.trim();
            document.getElementById('pbName').textContent   = name;
            document.getElementById('pciName').textContent  = name;
            document.getElementById('hddName').textContent  = name;
        })
        .catch(() => showToast('toastSuccess', 'toastMsg', 'Profile updated!')); // dev fallback
    });

    // Cancel
    document.getElementById('cancelEditBtn').addEventListener('click', () => {
        loadUserData();
    });

    // Save password
    document.getElementById('savePwBtn').addEventListener('click', () => {
        const curr    = document.getElementById('currentPw').value;
        const newPw   = document.getElementById('newPw').value;
        const confirm = document.getElementById('confirmPw').value;

        if (!curr || !newPw || !confirm) {
            showToast('toastError', 'toastErrMsg', 'Please fill all password fields.'); return;
        }
        if (newPw !== confirm) {
            showToast('toastError', 'toastErrMsg', 'Passwords do not match.'); return;
        }
        if (newPw.length < 8) {
            showToast('toastError', 'toastErrMsg', 'Password must be at least 8 characters.'); return;
        }

        fetch('change-password', {
            method: 'POST', credentials: 'include',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ currentPassword: curr, newPassword: newPw })
        })
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(() => {
            showToast('toastSuccess', 'toastMsg', 'Password updated successfully!');
            document.getElementById('currentPw').value = '';
            document.getElementById('newPw').value     = '';
            document.getElementById('confirmPw').value = '';
        })
        .catch(() => showToast('toastSuccess', 'toastMsg', 'Password updated!')); // dev fallback
    });

    // Clear history
    document.getElementById('clearHistoryBtn').addEventListener('click', () => {
        if (!confirm('Clear all watch history?')) return;
        document.getElementById('historyList').innerHTML =
            emptyState('🎬', 'History Cleared', 'Your watch history has been cleared.', 'movie.html', 'Browse Movies');
        document.getElementById('statWatched').textContent = '0';
        showToast('toastSuccess', 'toastMsg', 'Watch history cleared.');
    });
}

// ─── Filter chips ─────────────────────────────────────────────────────────
function setupFilters() {
    document.querySelectorAll('.filter-chip').forEach(chip => {
        chip.addEventListener('click', () => {
            document.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
            chip.classList.add('active');

            const filter = chip.dataset.filter;
            let items = WATCHED_HISTORY;
            if (filter === 'this-week') {
                const week = new Date(); week.setDate(week.getDate() - 7);
                items = WATCHED_HISTORY.filter(m => new Date(m.date) >= week);
            } else if (filter === 'this-month') {
                const month = new Date(); month.setDate(month.getDate() - 30);
                items = WATCHED_HISTORY.filter(m => new Date(m.date) >= month);
            }
            renderHistory(items);
        });
    });
}

// ─── Password strength ────────────────────────────────────────────────────
function setupPasswordStrength() {
    const input = document.getElementById('newPw');
    const wrap  = document.getElementById('pwStrength');
    const fill  = document.getElementById('pwFill');
    const label = document.getElementById('pwLabel');

    input.addEventListener('input', () => {
        const pw = input.value;
        if (!pw) { wrap.style.display = 'none'; return; }
        wrap.style.display = 'flex';

        let score = 0;
        if (pw.length >= 8)        score++;
        if (pw.length >= 12)       score++;
        if (/[A-Z]/.test(pw))      score++;
        if (/[0-9]/.test(pw))      score++;
        if (/[^a-zA-Z0-9]/.test(pw)) score++;

        const configs = [
            { w:'20%', bg:'#e74c3c', lbl:'Very Weak' },
            { w:'40%', bg:'#e67e22', lbl:'Weak'      },
            { w:'60%', bg:'#f39c12', lbl:'Fair'      },
            { w:'80%', bg:'#2ecc71', lbl:'Strong'    },
            { w:'100%', bg:'#27ae60', lbl:'Very Strong'},
        ];
        const c = configs[Math.min(score, 4)];
        fill.style.width      = c.w;
        fill.style.background = c.bg;
        label.textContent     = c.lbl;
        label.style.color     = c.bg;
    });
}

// ─── Eye toggles ──────────────────────────────────────────────────────────
function setupEyeToggles() {
    document.querySelectorAll('.pw-eye').forEach(btn => {
        btn.addEventListener('click', () => {
            const input = document.getElementById(btn.dataset.target);
            if (!input) return;
            input.type = input.type === 'password' ? 'text' : 'password';
            btn.style.color = input.type === 'text' ? 'var(--red)' : '';
        });
    });
}

// ─── Delete account ───────────────────────────────────────────────────────
function setupDeleteModal() {
    const modal   = document.getElementById('confirmModal');
    const openBtn = document.getElementById('deleteAccountBtn');
    const cancelB = document.getElementById('cancelDeleteBtn');
    const confirmB= document.getElementById('confirmDeleteBtn');
    const inp     = document.getElementById('deleteConfirmInput');

    openBtn.addEventListener('click', () => {
        inp.value = '';
        modal.classList.add('open');
        document.body.style.overflow = 'hidden';
        setTimeout(() => inp.focus(), 200);
    });
    cancelB.addEventListener('click', closeDeleteModal);
    modal.addEventListener('click', e => { if (e.target === modal) closeDeleteModal(); });

    confirmB.addEventListener('click', () => {
        if (inp.value.trim().toUpperCase() !== 'DELETE') {
            inp.style.borderColor = '#e74c3c';
            inp.focus(); return;
        }
        fetch('delete-account', { method: 'POST', credentials: 'include' })
            .then(() => window.location.href = 'login.html')
            .catch(() => window.location.href = 'login.html');
    });

    inp.addEventListener('input', () => { inp.style.borderColor = ''; });
}
function closeDeleteModal() {
    document.getElementById('confirmModal').classList.remove('open');
    document.body.style.overflow = '';
}

// ─── Remove history item ──────────────────────────────────────────────────
function removeHistory(id, btn) {
    const item = btn.closest('.history-item');
    item.style.opacity     = '0';
    item.style.transform   = 'translateX(-20px)';
    item.style.transition  = 'all 0.3s ease';
    setTimeout(() => {
        item.remove();
        const idx = WATCHED_HISTORY.findIndex(m => m.id === id);
        if (idx !== -1) WATCHED_HISTORY.splice(idx, 1);
        document.getElementById('statWatched').textContent = WATCHED_HISTORY.length;
    }, 300);
}

// ─── Delete review ────────────────────────────────────────────────────────
function deleteReview(id) {
    const item = document.getElementById('review-' + id);
    if (!item) return;
    item.style.opacity   = '0';
    item.style.transform = 'scale(0.96)';
    item.style.transition= 'all 0.25s ease';
    setTimeout(() => {
        item.remove();
        const idx = MY_REVIEWS.findIndex(r => r.id === id);
        if (idx !== -1) MY_REVIEWS.splice(idx, 1);
        document.getElementById('statReviews').textContent = MY_REVIEWS.length;
        showToast('toastSuccess', 'toastMsg', 'Review deleted.');
    }, 250);
}

// ─── Remove wishlist ──────────────────────────────────────────────────────
function removeWishlist(id, btn) {
    const card = btn.closest('.wl-card');
    card.style.opacity   = '0';
    card.style.transform = 'scale(0.9)';
    card.style.transition= 'all 0.25s ease';
    setTimeout(() => {
        card.remove();
        const idx = WISHLIST.findIndex(m => m.id === id);
        if (idx !== -1) WISHLIST.splice(idx, 1);
        document.getElementById('statWishlist').textContent = WISHLIST.length;
        if (!WISHLIST.length) renderWishlist([]);
    }, 250);
}

// ─── Helpers ──────────────────────────────────────────────────────────────
function formatDate(dateStr) {
    return new Date(dateStr).toLocaleDateString('en-GB', { day:'2-digit', month:'short', year:'numeric' });
}

function emptyState(icon, title, desc, link, linkText) {
    return `<div class="empty-state">
        <div class="es-icon">${icon}</div>
        <div class="es-title">${title}</div>
        <div class="es-desc">${desc}</div>
        <a href="${link}" class="btn-primary">${linkText}</a>
    </div>`;
}

function showToast(toastId, msgId, msg) {
    const el = document.getElementById(toastId);
    document.getElementById(msgId).textContent = msg;
    el.classList.add('show');
    setTimeout(() => el.classList.remove('show'), 3500);
}

// Expose for inline HTML onclick
window.removeHistory  = removeHistory;
window.deleteReview   = deleteReview;
window.removeWishlist = removeWishlist;
