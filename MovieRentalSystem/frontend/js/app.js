// ═══════════════════════════════════════
//  CINERENT — Main Application JS
// ═══════════════════════════════════════

"use strict";

// ── Utility ──────────────────────────────────────
const $ = (sel, ctx = document) => ctx.querySelector(sel);
const $$ = (sel, ctx = document) => [...ctx.querySelectorAll(sel)];
const esc = s => String(s).replace(/</g, '&lt;').replace(/>/g, '&gt;');

// ── State ─────────────────────────────────────────
const state = {
  currentPage: 'dashboard',
  sidebarOpen: false,
  data: {
    users: JSON.parse(localStorage.getItem('cr_users') || '[]'),
    movies: JSON.parse(localStorage.getItem('cr_movies') || '[]'),
    rentals: JSON.parse(localStorage.getItem('cr_rentals') || '[]'),
    reviews: JSON.parse(localStorage.getItem('cr_reviews') || '[]'),
    payments: JSON.parse(localStorage.getItem('cr_payments') || '[]'),
    admins: JSON.parse(localStorage.getItem('cr_admins') || '[]'),
  },
  editTarget: null,
  ratingSelected: 0,
};

// Seed demo data if empty
function seedData() {
  if (!state.data.users.length) {
    state.data.users = [
      { id:'U001', name:'Alex Morgan', email:'alex@mail.com', password:'****', membership:'Premium', status:'Active', joined:'2024-01-15' },
      { id:'U002', name:'Jordan Lee', email:'jordan@mail.com', password:'****', membership:'Regular', status:'Active', joined:'2024-02-20' },
      { id:'U003', name:'Casey Kim', email:'casey@mail.com', password:'****', membership:'Regular', status:'Inactive', joined:'2024-03-10' },
      { id:'U004', name:'Riley Smith', email:'riley@mail.com', password:'****', membership:'Premium', status:'Active', joined:'2024-04-02' },
    ];
  }
  if (!state.data.movies.length) {
    state.data.movies = [
      { id:'M001', title:'Inception', genre:'Sci-Fi', director:'Christopher Nolan', year:2010, format:'Digital', available:true, rating:4.8 },
      { id:'M002', title:'The Dark Knight', genre:'Action', director:'Christopher Nolan', year:2008, format:'DVD', available:true, rating:4.9 },
      { id:'M003', title:'Interstellar', genre:'Sci-Fi', director:'Christopher Nolan', year:2014, format:'Digital', available:false, rating:4.7 },
      { id:'M004', title:'Pulp Fiction', genre:'Crime', director:'Quentin Tarantino', year:1994, format:'DVD', available:true, rating:4.6 },
      { id:'M005', title:'The Matrix', genre:'Sci-Fi', director:'Wachowski Sisters', year:1999, format:'Digital', available:true, rating:4.5 },
      { id:'M006', title:'Parasite', genre:'Thriller', director:'Bong Joon-ho', year:2019, format:'DVD', available:true, rating:4.7 },
    ];
  }
  if (!state.data.rentals.length) {
    state.data.rentals = [
      { id:'R001', userId:'U001', movieId:'M001', rentalDate:'2024-04-01', dueDate:'2024-04-15', returnDate:null, status:'Active', type:'Premium' },
      { id:'R002', userId:'U002', movieId:'M002', rentalDate:'2024-03-25', dueDate:'2024-04-01', returnDate:'2024-04-03', status:'Overdue', type:'Regular' },
      { id:'R003', userId:'U004', movieId:'M005', rentalDate:'2024-04-05', dueDate:'2024-04-19', returnDate:null, status:'Active', type:'Premium' },
    ];
  }
  if (!state.data.reviews.length) {
    state.data.reviews = [
      { id:'RV001', userId:'U001', movieId:'M001', rating:5, comment:'Absolutely mind-bending! Nolan at his best.', date:'2024-03-28', type:'Verified', status:'Approved' },
      { id:'RV002', userId:'U002', movieId:'M002', rating:5, comment:'The greatest superhero film ever made.', date:'2024-03-30', type:'Public', status:'Approved' },
      { id:'RV003', userId:'U003', movieId:'M004', rating:4, comment:'Tarantino classic. Dialogue is superb.', date:'2024-04-01', type:'Public', status:'Pending' },
    ];
  }
  if (!state.data.payments.length) {
    state.data.payments = [
      { id:'P001', userId:'U001', rentalId:'R001', amount:5.99, type:'Rental', status:'Paid', date:'2024-04-01' },
      { id:'P002', userId:'U002', rentalId:'R002', amount:3.99, type:'Fine', status:'Pending', date:'2024-04-03' },
      { id:'P003', userId:'U004', rentalId:'R003', amount:5.99, type:'Rental', status:'Paid', date:'2024-04-05' },
    ];
  }
  if (!state.data.admins.length) {
    state.data.admins = [
      { id:'A001', name:'System Admin', email:'admin@cinerent.com', role:'SuperAdmin', region:'Global', status:'Active', lastLogin:'2024-04-10' },
      { id:'A002', name:'Mod User', email:'mod@cinerent.com', role:'ModeratorAdmin', region:'Asia', status:'Active', lastLogin:'2024-04-09' },
    ];
  }
  saveAll();
}

function saveAll() {
  Object.keys(state.data).forEach(k => {
    localStorage.setItem('cr_' + k, JSON.stringify(state.data[k]));
  });
}

// ── Toast ──────────────────────────────────────────
function toast(msg, type = 'default') {
  const icons = { default: '🎬', success: '✅', warning: '⚠️', error: '❌' };
  const tc = $('#toast-container');
  const t = document.createElement('div');
  t.className = `toast ${type}`;
  t.innerHTML = `<span>${icons[type]}</span><span>${msg}</span>`;
  tc.appendChild(t);
  setTimeout(() => t.remove(), 3200);
}

// ── Navigation ──────────────────────────────────────
function navigateTo(page) {
  state.currentPage = page;
  $$('.nav-item').forEach(n => n.classList.toggle('active', n.dataset.page === page));
  $$('.page-view').forEach(v => v.classList.toggle('active', v.id === `page-${page}`));

  const titles = {
    dashboard: ['Overview', 'Dashboard'],
    users: ['Users', 'User Management'],
    movies: ['Catalog', 'Movie Management'],
    rentals: ['Rentals', 'Rental Management'],
    reviews: ['Reviews', 'Review & Ratings'],
    payments: ['Finance', 'Payment & Fines'],
    admins: ['Admin', 'Admin Management'],
  };
  if (titles[page]) {
    $('#breadcrumb-section').textContent = titles[page][0];
    $('#breadcrumb-page').textContent = titles[page][1];
  }

  // Close sidebar on mobile
  if (window.innerWidth <= 900) {
    state.sidebarOpen = false;
    $('#sidebar').classList.remove('open');
  }

  renderPage(page);
}

function renderPage(page) {
  switch (page) {
    case 'dashboard': renderDashboard(); break;
    case 'users': renderUsers(); break;
    case 'movies': renderMovies(); break;
    case 'rentals': renderRentals(); break;
    case 'reviews': renderReviews(); break;
    case 'payments': renderPayments(); break;
    case 'admins': renderAdmins(); break;
  }
}

// ── Modal Helpers ───────────────────────────────────
function openModal(id) {
  const m = $(`#${id}`);
  if (m) { m.classList.add('open'); document.body.style.overflow = 'hidden'; }
}

function closeModal(id) {
  const m = $(`#${id}`);
  if (m) { m.classList.remove('open'); document.body.style.overflow = ''; }
}

function closeAllModals() {
  $$('.modal-overlay').forEach(m => m.classList.remove('open'));
  document.body.style.overflow = '';
}

// ── ID Generator ────────────────────────────────────
function genId(prefix) {
  return prefix + Date.now().toString(36).toUpperCase().slice(-4);
}

// ── Lookup Helpers ───────────────────────────────────
function getUserName(id) {
  const u = state.data.users.find(u => u.id === id);
  return u ? u.name : id;
}
function getMovieTitle(id) {
  const m = state.data.movies.find(m => m.id === id);
  return m ? m.title : id;
}

// ═══════════════════════════════════════
//  DASHBOARD
// ═══════════════════════════════════════
function renderDashboard() {
  const { users, movies, rentals, reviews, payments, admins } = state.data;
  const activeRentals = rentals.filter(r => r.status === 'Active').length;
  const pendingPayments = payments.filter(p => p.status === 'Pending').length;
  const totalRevenue = payments.filter(p => p.status === 'Paid').reduce((s, p) => s + p.amount, 0);

  $('#dash-stat-users').textContent = users.length;
  $('#dash-stat-movies').textContent = movies.length;
  $('#dash-stat-rentals').textContent = activeRentals;
  $('#dash-stat-revenue').textContent = '$' + totalRevenue.toFixed(2);

  // Recent activity
  const feed = $('#activity-feed');
  const activities = [
    ...rentals.map(r => ({ text: `<strong>${getUserName(r.userId)}</strong> rented <strong>${getMovieTitle(r.movieId)}</strong>`, time: r.rentalDate, dot: 'red' })),
    ...reviews.map(r => ({ text: `<strong>${getUserName(r.userId)}</strong> reviewed <strong>${getMovieTitle(r.movieId)}</strong>`, time: r.date, dot: 'green' })),
    ...payments.filter(p => p.status === 'Paid').map(p => ({ text: `Payment of <strong>$${p.amount}</strong> received from <strong>${getUserName(p.userId)}</strong>`, time: p.date, dot: 'yellow' })),
  ].sort((a,b) => b.time.localeCompare(a.time)).slice(0, 8);

  feed.innerHTML = activities.length ? activities.map(a => `
    <div class="activity-item">
      <div class="activity-dot ${a.dot}"></div>
      <div class="activity-text">${a.text}</div>
      <div class="activity-time">${a.time}</div>
    </div>
  `).join('') : `<div class="empty-state"><div class="empty-icon">📋</div><div class="empty-title">No Activity Yet</div></div>`;

  // Top movies by rental
  const movieRentalCount = {};
  rentals.forEach(r => movieRentalCount[r.movieId] = (movieRentalCount[r.movieId] || 0) + 1);
  const topMovies = movies
    .map(m => ({ ...m, count: movieRentalCount[m.id] || 0 }))
    .sort((a,b) => b.count - a.count)
    .slice(0, 5);

  $('#top-movies').innerHTML = topMovies.map(m => `
    <tr>
      <td class="td-primary">${esc(m.title)}</td>
      <td>${esc(m.genre)}</td>
      <td>${m.count} <span style="color:var(--white-30)">rentals</span></td>
      <td><span class="badge ${m.available ? 'badge-available' : 'badge-inactive'}">
        <span class="badge-dot"></span>${m.available ? 'Available' : 'Rented'}
      </span></td>
    </tr>
  `).join('');

  // Pending fines summary
  const pendingFines = payments.filter(p => p.status === 'Pending');
  $('#pending-fines').innerHTML = pendingFines.length ? pendingFines.map(p => `
    <tr>
      <td class="td-primary">${getUserName(p.userId)}</td>
      <td>${p.type}</td>
      <td style="color:var(--red);font-weight:700">$${p.amount.toFixed(2)}</td>
      <td><span class="badge badge-pending"><span class="badge-dot"></span>Pending</span></td>
    </tr>
  `).join('') : `<tr><td colspan="4" style="text-align:center;color:var(--white-30);padding:30px">No pending fines</td></tr>`;
}

// ═══════════════════════════════════════
//  USERS
// ═══════════════════════════════════════
function renderUsers(filter = '') {
  const users = state.data.users.filter(u =>
    !filter || u.name.toLowerCase().includes(filter) || u.email.toLowerCase().includes(filter) || u.id.toLowerCase().includes(filter)
  );
  $('#users-count').textContent = users.length + ' users';
  $('#users-tbody').innerHTML = users.length ? users.map(u => `
    <tr>
      <td class="td-primary">
        <div style="display:flex;align-items:center;gap:10px">
          <div style="width:32px;height:32px;background:var(--red);border-radius:50%;display:flex;align-items:center;justify-content:center;font-family:var(--font-display);font-size:14px;flex-shrink:0">${u.name[0]}</div>
          ${esc(u.name)}
        </div>
      </td>
      <td>${u.id}</td>
      <td>${esc(u.email)}</td>
      <td><span class="badge ${u.membership === 'Premium' ? 'badge-premium' : 'badge-regular'}"><span class="badge-dot"></span>${u.membership}</span></td>
      <td><span class="badge ${u.status === 'Active' ? 'badge-active' : 'badge-inactive'}"><span class="badge-dot"></span>${u.status}</span></td>
      <td>${u.joined}</td>
      <td>
        <div style="display:flex;gap:6px">
          <button class="btn btn-ghost btn-icon btn-sm" onclick="editUser('${u.id}')" title="Edit">✏️</button>
          <button class="btn btn-ghost btn-icon btn-sm" onclick="deleteItem('users','${u.id}')" title="Delete">🗑️</button>
        </div>
      </td>
    </tr>
  `).join('') : `<tr><td colspan="7"><div class="empty-state"><div class="empty-icon">👤</div><div class="empty-title">No Users Found</div></div></td></tr>`;
}

function editUser(id) {
  const u = state.data.users.find(x => x.id === id);
  if (!u) return;
  state.editTarget = u;
  $('#user-form-title').textContent = 'Edit User';
  $('#uf-name').value = u.name;
  $('#uf-email').value = u.email;
  $('#uf-membership').value = u.membership;
  $('#uf-status').value = u.status;
  openModal('modal-user');
}

function saveUser() {
  const name = $('#uf-name').value.trim();
  const email = $('#uf-email').value.trim();
  const membership = $('#uf-membership').value;
  const status = $('#uf-status').value;
  if (!name || !email) { toast('Please fill all required fields.', 'error'); return; }

  if (state.editTarget) {
    Object.assign(state.editTarget, { name, email, membership, status });
    toast('User updated successfully!', 'success');
  } else {
    state.data.users.push({ id: genId('U'), name, email, password: '****', membership, status, joined: new Date().toISOString().slice(0,10) });
    toast('User created successfully!', 'success');
  }
  saveAll();
  closeModal('modal-user');
  state.editTarget = null;
  renderUsers();
}

// ═══════════════════════════════════════
//  MOVIES
// ═══════════════════════════════════════
function renderMovies(filter = '', genreFilter = '') {
  let movies = state.data.movies;
  if (filter) movies = movies.filter(m => m.title.toLowerCase().includes(filter) || m.director.toLowerCase().includes(filter) || m.genre.toLowerCase().includes(filter));
  if (genreFilter) movies = movies.filter(m => m.genre === genreFilter);

  $('#movies-count').textContent = movies.length + ' movies';
  const grid = $('#movies-grid');
  const movieEmojis = { 'Sci-Fi':'🚀', 'Action':'💥', 'Crime':'🔫', 'Thriller':'🎭', 'Drama':'🎪', 'Comedy':'😂', 'Horror':'👻', 'Romance':'💖' };

  grid.innerHTML = movies.length ? movies.map(m => `
    <div class="movie-card">
      <div class="movie-poster">
        <div class="poster-placeholder">${movieEmojis[m.genre] || '🎬'}</div>
        <div class="movie-format">${m.format}</div>
        <div class="movie-poster-overlay">
          <button class="btn btn-primary btn-sm" onclick="event.stopPropagation();editMovie('${m.id}')">✏️ Edit</button>
          <button class="btn btn-danger btn-sm" onclick="event.stopPropagation();deleteItem('movies','${m.id}')">🗑️</button>
        </div>
      </div>
      <div class="movie-card-info">
        <div class="movie-title">${esc(m.title)}</div>
        <div class="movie-meta">${m.genre} · ${m.year}</div>
        <div style="display:flex;align-items:center;justify-content:space-between">
          <div class="movie-rating">${[1,2,3,4,5].map(i => `<span class="star${i <= Math.round(m.rating) ? '' : ' empty'}">★</span>`).join('')}</div>
          <span class="badge ${m.available ? 'badge-available' : 'badge-inactive'}" style="font-size:10px">${m.available ? 'Available' : 'Rented'}</span>
        </div>
      </div>
    </div>
  `).join('') : `<div style="grid-column:1/-1"><div class="empty-state"><div class="empty-icon">🎬</div><div class="empty-title">No Movies Found</div><div class="empty-desc">Try adjusting your search or filters.</div></div></div>`;
}

function editMovie(id) {
  const m = state.data.movies.find(x => x.id === id);
  if (!m) return;
  state.editTarget = m;
  $('#movie-form-title').textContent = 'Edit Movie';
  $('#mf-title').value = m.title;
  $('#mf-genre').value = m.genre;
  $('#mf-director').value = m.director;
  $('#mf-year').value = m.year;
  $('#mf-format').value = m.format;
  $('#mf-available').value = m.available ? 'true' : 'false';
  openModal('modal-movie');
}

function saveMovie() {
  const title = $('#mf-title').value.trim();
  const genre = $('#mf-genre').value;
  const director = $('#mf-director').value.trim();
  const year = parseInt($('#mf-year').value);
  const format = $('#mf-format').value;
  const available = $('#mf-available').value === 'true';
  if (!title || !director || !year) { toast('Please fill all required fields.', 'error'); return; }

  if (state.editTarget) {
    Object.assign(state.editTarget, { title, genre, director, year, format, available });
    toast('Movie updated!', 'success');
  } else {
    state.data.movies.push({ id: genId('M'), title, genre, director, year, format, available, rating: 0 });
    toast('Movie added to catalog!', 'success');
  }
  saveAll(); closeModal('modal-movie'); state.editTarget = null; renderMovies();
}

// ═══════════════════════════════════════
//  RENTALS
// ═══════════════════════════════════════
function renderRentals(statusFilter = '') {
  let rentals = state.data.rentals;
  if (statusFilter) rentals = rentals.filter(r => r.status === statusFilter);
  $('#rentals-count').textContent = rentals.length + ' records';

  const today = new Date().toISOString().slice(0,10);
  $('#rentals-tbody').innerHTML = rentals.length ? rentals.map(r => {
    const overdue = !r.returnDate && r.dueDate < today;
    const statusClass = r.returnDate ? 'badge-returned' : overdue ? 'badge-overdue' : 'badge-active';
    const statusLabel = r.returnDate ? 'Returned' : overdue ? 'Overdue' : 'Active';
    return `
      <tr>
        <td class="td-primary">${r.id}</td>
        <td>${getUserName(r.userId)}</td>
        <td>${getMovieTitle(r.movieId)}</td>
        <td><span class="badge ${r.type === 'Premium' ? 'badge-premium' : 'badge-regular'}"><span class="badge-dot"></span>${r.type}</span></td>
        <td>${r.rentalDate}</td>
        <td>${r.dueDate}</td>
        <td>${r.returnDate || '<span style="color:var(--white-30)">—</span>'}</td>
        <td><span class="badge ${statusClass}"><span class="badge-dot"></span>${statusLabel}</span></td>
        <td>
          <div style="display:flex;gap:6px">
            ${!r.returnDate ? `<button class="btn btn-outline btn-sm" onclick="processReturn('${r.id}')">↩ Return</button>` : ''}
            <button class="btn btn-ghost btn-icon btn-sm" onclick="deleteItem('rentals','${r.id}')">🗑️</button>
          </div>
        </td>
      </tr>
    `;
  }).join('') : `<tr><td colspan="9"><div class="empty-state"><div class="empty-icon">📼</div><div class="empty-title">No Rentals Found</div></div></td></tr>`;
}

function saveRental() {
  const userId = $('#rf-user').value;
  const movieId = $('#rf-movie').value;
  const type = $('#rf-type').value;
  const rentalDate = $('#rf-date').value || new Date().toISOString().slice(0,10);
  if (!userId || !movieId) { toast('Please select user and movie.', 'error'); return; }

  const days = type === 'Premium' ? 14 : 7;
  const due = new Date(rentalDate);
  due.setDate(due.getDate() + days);

  state.data.rentals.push({
    id: genId('R'), userId, movieId, rentalDate,
    dueDate: due.toISOString().slice(0,10),
    returnDate: null, status: 'Active', type
  });
  // Mark movie unavailable
  const mv = state.data.movies.find(m => m.id === movieId);
  if (mv) mv.available = false;

  saveAll(); closeModal('modal-rental'); renderRentals(); renderMovies();
  toast('Rental recorded!', 'success');
}

function processReturn(rentalId) {
  const r = state.data.rentals.find(x => x.id === rentalId);
  if (!r) return;
  const today = new Date().toISOString().slice(0,10);
  r.returnDate = today;
  r.status = r.dueDate < today ? 'Overdue' : 'Returned';

  const mv = state.data.movies.find(m => m.id === r.movieId);
  if (mv) mv.available = true;

  if (r.dueDate < today) {
    const daysLate = Math.ceil((new Date(today) - new Date(r.dueDate)) / 86400000);
    const fineRate = r.type === 'Premium' ? 0.75 : 1.00;
    const fine = +(daysLate * fineRate).toFixed(2);
    state.data.payments.push({ id: genId('P'), userId: r.userId, rentalId, amount: fine, type: 'Fine', status: 'Pending', date: today });
    toast(`Return processed. Fine of $${fine} added for ${daysLate} day(s) late.`, 'warning');
  } else {
    toast('Return processed successfully!', 'success');
  }

  saveAll(); renderRentals(); renderPayments();
}

function populateRentalDropdowns() {
  const users = state.data.users.filter(u => u.status === 'Active');
  const movies = state.data.movies.filter(m => m.available);
  $('#rf-user').innerHTML = `<option value="">Select User</option>` + users.map(u => `<option value="${u.id}">${esc(u.name)} (${u.membership})</option>`).join('');
  $('#rf-movie').innerHTML = `<option value="">Select Movie</option>` + movies.map(m => `<option value="${m.id}">${esc(m.title)} [${m.format}]</option>`).join('');
}

// ═══════════════════════════════════════
//  REVIEWS
// ═══════════════════════════════════════
function renderReviews(filter = '') {
  let reviews = state.data.reviews;
  if (filter) reviews = reviews.filter(r => r.status === filter);
  $('#reviews-count').textContent = reviews.length + ' reviews';

  $('#reviews-tbody').innerHTML = reviews.length ? reviews.map(r => {
    const stars = [1,2,3,4,5].map(i => `<span class="star${i <= r.rating ? '' : ' empty'}">★</span>`).join('');
    return `
      <tr>
        <td class="td-primary">${r.id}</td>
        <td>${getUserName(r.userId)}</td>
        <td>${getMovieTitle(r.movieId)}</td>
        <td><div style="display:flex;gap:2px">${stars}</div></td>
        <td style="max-width:220px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis">${esc(r.comment)}</td>
        <td><span class="badge ${r.type === 'Verified' ? 'badge-verified' : 'badge-public'}"><span class="badge-dot"></span>${r.type}</span></td>
        <td><span class="badge ${r.status === 'Approved' ? 'badge-active' : 'badge-pending'}"><span class="badge-dot"></span>${r.status}</span></td>
        <td>${r.date}</td>
        <td>
          <div style="display:flex;gap:6px">
            ${r.status === 'Pending' ? `<button class="btn btn-outline btn-sm" onclick="approveReview('${r.id}')">✓ Approve</button>` : ''}
            <button class="btn btn-ghost btn-icon btn-sm" onclick="deleteItem('reviews','${r.id}')">🗑️</button>
          </div>
        </td>
      </tr>
    `;
  }).join('') : `<tr><td colspan="9"><div class="empty-state"><div class="empty-icon">⭐</div><div class="empty-title">No Reviews Found</div></div></td></tr>`;
}

function approveReview(id) {
  const r = state.data.reviews.find(x => x.id === id);
  if (r) { r.status = 'Approved'; saveAll(); renderReviews(); toast('Review approved!', 'success'); }
}

function setRating(val) {
  state.ratingSelected = val;
  $$('#star-input span').forEach((s, i) => s.classList.toggle('active', i < val));
}

function saveReview() {
  const userId = $('#rv-user').value;
  const movieId = $('#rv-movie').value;
  const comment = $('#rv-comment').value.trim();
  if (!userId || !movieId || !state.ratingSelected || !comment) { toast('Please fill all fields and select a rating.', 'error'); return; }

  const isRenter = state.data.rentals.some(r => r.userId === userId && r.movieId === movieId);
  state.data.reviews.push({
    id: genId('RV'), userId, movieId, rating: state.ratingSelected,
    comment, date: new Date().toISOString().slice(0,10),
    type: isRenter ? 'Verified' : 'Public', status: 'Pending'
  });
  saveAll(); closeModal('modal-review'); state.ratingSelected = 0; renderReviews();
  toast('Review submitted for moderation!', 'success');
}

function populateReviewDropdowns() {
  const users = state.data.users;
  const movies = state.data.movies;
  $('#rv-user').innerHTML = `<option value="">Select User</option>` + users.map(u => `<option value="${u.id}">${esc(u.name)}</option>`).join('');
  $('#rv-movie').innerHTML = `<option value="">Select Movie</option>` + movies.map(m => `<option value="${m.id}">${esc(m.title)}</option>`).join('');
}

// ═══════════════════════════════════════
//  PAYMENTS
// ═══════════════════════════════════════
function renderPayments(filter = '') {
  let payments = state.data.payments;
  if (filter) payments = payments.filter(p => p.status === filter || p.type === filter);
  $('#payments-count').textContent = payments.length + ' transactions';

  const totalRevenue = state.data.payments.filter(p => p.status === 'Paid').reduce((s,p) => s + p.amount, 0);
  const pendingTotal = state.data.payments.filter(p => p.status === 'Pending').reduce((s,p) => s + p.amount, 0);
  $('#pay-total-revenue').textContent = '$' + totalRevenue.toFixed(2);
  $('#pay-pending-total').textContent = '$' + pendingTotal.toFixed(2);

  $('#payments-tbody').innerHTML = payments.length ? payments.map(p => `
    <tr>
      <td class="td-primary">${p.id}</td>
      <td>${getUserName(p.userId)}</td>
      <td>${p.rentalId}</td>
      <td><span class="badge ${p.type === 'Fine' ? 'badge-overdue' : 'badge-regular'}"><span class="badge-dot"></span>${p.type}</span></td>
      <td style="font-weight:700;color:${p.type === 'Fine' ? 'var(--red)' : 'var(--white)'}">$${p.amount.toFixed(2)}</td>
      <td><span class="badge ${p.status === 'Paid' ? 'badge-paid' : 'badge-pending'}"><span class="badge-dot"></span>${p.status}</span></td>
      <td>${p.date}</td>
      <td>
        <div style="display:flex;gap:6px">
          ${p.status === 'Pending' ? `<button class="btn btn-primary btn-sm" onclick="markPaid('${p.id}')">💳 Pay</button>` : `<button class="btn btn-ghost btn-sm" onclick="showReceipt('${p.id}')">🧾 Receipt</button>`}
          <button class="btn btn-ghost btn-icon btn-sm" onclick="deleteItem('payments','${p.id}')">🗑️</button>
        </div>
      </td>
    </tr>
  `).join('') : `<tr><td colspan="8"><div class="empty-state"><div class="empty-icon">💳</div><div class="empty-title">No Payments Found</div></div></td></tr>`;
}

function markPaid(id) {
  const p = state.data.payments.find(x => x.id === id);
  if (p) { p.status = 'Paid'; saveAll(); renderPayments(); toast('Payment marked as paid!', 'success'); }
}

function showReceipt(id) {
  const p = state.data.payments.find(x => x.id === id);
  if (!p) return;
  $('#receipt-content').innerHTML = `
    <div class="receipt">
      <div class="receipt-header">
        <div class="receipt-logo">🎬 CINERENT</div>
        <div class="receipt-id">RECEIPT #${p.id}</div>
      </div>
      <div class="receipt-row"><span class="label">Customer</span><span>${getUserName(p.userId)}</span></div>
      <div class="receipt-row"><span class="label">Rental ID</span><span>${p.rentalId}</span></div>
      <div class="receipt-row"><span class="label">Type</span><span>${p.type}</span></div>
      <div class="receipt-row"><span class="label">Date</span><span>${p.date}</span></div>
      <div class="receipt-row"><span class="label">Status</span><span>${p.status}</span></div>
      <div class="receipt-total"><span>TOTAL</span><span class="amount">$${p.amount.toFixed(2)}</span></div>
    </div>
  `;
  openModal('modal-receipt');
}

// ═══════════════════════════════════════
//  ADMINS
// ═══════════════════════════════════════
function renderAdmins(filter = '') {
  const admins = state.data.admins.filter(a => !filter || a.name.toLowerCase().includes(filter) || a.email.toLowerCase().includes(filter));
  $('#admins-count').textContent = admins.length + ' admins';

  $('#admins-tbody').innerHTML = admins.length ? admins.map(a => `
    <tr>
      <td class="td-primary">
        <div style="display:flex;align-items:center;gap:10px">
          <div style="width:32px;height:32px;background:${a.role === 'SuperAdmin' ? 'var(--red)' : '#374151'};border-radius:50%;display:flex;align-items:center;justify-content:center;font-family:var(--font-display);font-size:14px;flex-shrink:0">${a.name[0]}</div>
          ${esc(a.name)}
        </div>
      </td>
      <td>${a.id}</td>
      <td>${esc(a.email)}</td>
      <td><span class="badge badge-admin"><span class="badge-dot"></span>${a.role}</span></td>
      <td>${esc(a.region)}</td>
      <td><span class="badge ${a.status === 'Active' ? 'badge-active' : 'badge-inactive'}"><span class="badge-dot"></span>${a.status}</span></td>
      <td>${a.lastLogin}</td>
      <td>
        <div style="display:flex;gap:6px">
          <button class="btn btn-ghost btn-icon btn-sm" onclick="editAdmin('${a.id}')">✏️</button>
          ${a.role !== 'SuperAdmin' ? `<button class="btn btn-ghost btn-icon btn-sm" onclick="deleteItem('admins','${a.id}')">🗑️</button>` : ''}
        </div>
      </td>
    </tr>
  `).join('') : `<tr><td colspan="8"><div class="empty-state"><div class="empty-icon">🛡️</div><div class="empty-title">No Admins Found</div></div></td></tr>`;
}

function editAdmin(id) {
  const a = state.data.admins.find(x => x.id === id);
  if (!a) return;
  state.editTarget = a;
  $('#admin-form-title').textContent = 'Edit Admin';
  $('#af-name').value = a.name;
  $('#af-email').value = a.email;
  $('#af-role').value = a.role;
  $('#af-region').value = a.region;
  $('#af-status').value = a.status;
  openModal('modal-admin');
}

function saveAdmin() {
  const name = $('#af-name').value.trim();
  const email = $('#af-email').value.trim();
  const role = $('#af-role').value;
  const region = $('#af-region').value.trim();
  const status = $('#af-status').value;
  if (!name || !email) { toast('Please fill all required fields.', 'error'); return; }

  if (state.editTarget) {
    Object.assign(state.editTarget, { name, email, role, region, status });
    toast('Admin updated!', 'success');
  } else {
    state.data.admins.push({ id: genId('A'), name, email, role, region, status, lastLogin: '—' });
    toast('Admin account created!', 'success');
  }
  saveAll(); closeModal('modal-admin'); state.editTarget = null; renderAdmins();
}

// ═══════════════════════════════════════
//  DELETE (with confirm)
// ═══════════════════════════════════════
let _deleteCallback = null;

function deleteItem(collection, id) {
  _deleteCallback = () => {
    state.data[collection] = state.data[collection].filter(x => x.id !== id);
    saveAll();
    renderPage(state.currentPage);
    toast('Record deleted.', 'default');
  };
  openModal('modal-confirm');
}

// ═══════════════════════════════════════
//  INIT
// ═══════════════════════════════════════
document.addEventListener('DOMContentLoaded', () => {
  seedData();

  // Nav
  $$('.nav-item').forEach(item => {
    item.addEventListener('click', () => navigateTo(item.dataset.page));
  });

  // Hamburger
  $('#hamburger').addEventListener('click', () => {
    state.sidebarOpen = !state.sidebarOpen;
    $('#sidebar').classList.toggle('open', state.sidebarOpen);
  });

  // Close sidebar on overlay click (mobile)
  document.addEventListener('click', e => {
    if (window.innerWidth <= 900 && state.sidebarOpen && !$('#sidebar').contains(e.target) && e.target !== $('#hamburger')) {
      state.sidebarOpen = false;
      $('#sidebar').classList.remove('open');
    }
  });

  // Close modals on overlay click
  $$('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', e => {
      if (e.target === overlay) closeAllModals();
    });
  });

  // Confirm delete
  $('#confirm-yes').addEventListener('click', () => {
    if (_deleteCallback) { _deleteCallback(); _deleteCallback = null; }
    closeModal('modal-confirm');
  });
  $('#confirm-no').addEventListener('click', () => closeModal('modal-confirm'));

  // User form
  $('#btn-add-user').addEventListener('click', () => {
    state.editTarget = null;
    $('#user-form-title').textContent = 'Add User';
    $('#user-form').reset();
    openModal('modal-user');
  });
  $('#save-user').addEventListener('click', saveUser);

  // Movie form
  $('#btn-add-movie').addEventListener('click', () => {
    state.editTarget = null;
    $('#movie-form-title').textContent = 'Add Movie';
    $('#movie-form').reset();
    openModal('modal-movie');
  });
  $('#save-movie').addEventListener('click', saveMovie);

  // Rental form
  $('#btn-add-rental').addEventListener('click', () => {
    populateRentalDropdowns();
    $('#rental-form').reset();
    openModal('modal-rental');
  });
  $('#save-rental').addEventListener('click', saveRental);

  // Review form
  $('#btn-add-review').addEventListener('click', () => {
    state.ratingSelected = 0;
    populateReviewDropdowns();
    $$('#star-input span').forEach(s => s.classList.remove('active'));
    $('#review-form').reset();
    openModal('modal-review');
  });
  $('#save-review').addEventListener('click', saveReview);

  // Admin form
  $('#btn-add-admin').addEventListener('click', () => {
    state.editTarget = null;
    $('#admin-form-title').textContent = 'Add Admin';
    $('#admin-form').reset();
    openModal('modal-admin');
  });
  $('#save-admin').addEventListener('click', saveAdmin);

  // Live search
  $('#user-search').addEventListener('input', e => renderUsers(e.target.value.toLowerCase()));
  $('#movie-search').addEventListener('input', e => renderMovies(e.target.value.toLowerCase(), $('#movie-genre-filter').value));
  $('#movie-genre-filter').addEventListener('change', e => renderMovies($('#movie-search').value.toLowerCase(), e.target.value));
  $('#admin-search').addEventListener('input', e => renderAdmins(e.target.value.toLowerCase()));

  // Tab/filter buttons
  $$('[data-rental-filter]').forEach(btn => {
    btn.addEventListener('click', () => {
      $$('[data-rental-filter]').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      renderRentals(btn.dataset.rentalFilter);
    });
  });

  $$('[data-review-filter]').forEach(btn => {
    btn.addEventListener('click', () => {
      $$('[data-review-filter]').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      renderReviews(btn.dataset.reviewFilter);
    });
  });

  $$('[data-pay-filter]').forEach(btn => {
    btn.addEventListener('click', () => {
      $$('[data-pay-filter]').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      renderPayments(btn.dataset.payFilter);
    });
  });

  // Close buttons
  $$('.modal-close').forEach(btn => {
    btn.addEventListener('click', () => closeAllModals());
  });

  // Star rating input
  $$('#star-input span').forEach((s, i) => {
    s.addEventListener('click', () => setRating(i + 1));
    s.addEventListener('mouseover', () => {
      $$('#star-input span').forEach((x, j) => x.classList.toggle('active', j <= i));
    });
  });
  $('#star-input').addEventListener('mouseleave', () => {
    $$('#star-input span').forEach((s, i) => s.classList.toggle('active', i < state.ratingSelected));
  });

  // Topbar global search
  $('#global-search').addEventListener('input', e => {
    const q = e.target.value.toLowerCase();
    if (q.length < 2) return;
    const pages = {
      users: state.data.users.some(u => u.name.toLowerCase().includes(q)),
      movies: state.data.movies.some(m => m.title.toLowerCase().includes(q)),
    };
    if (pages.movies) navigateTo('movies');
    else if (pages.users) navigateTo('users');
  });

  // Start on dashboard
  navigateTo('dashboard');
});
