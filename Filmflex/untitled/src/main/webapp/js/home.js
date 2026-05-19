// ============================================================
// FILMFLEX — home.js   (real posters + video player)
// ============================================================

// Real TMDB poster images (w500) + YouTube trailer IDs
const MOVIES = [
    {
        id:1, title:"Inception", rating:8.8, year:2010, genre:"Sci-Fi", duration:"2h 28m",
        poster:"https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/s3TBrRGB1iav7gFOCNx3H31MoES.jpg",
        trailer:"YoHD9XEInc0",
        desc:"A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."
    },
    {
        id:2, title:"The Dark Knight", rating:9.0, year:2008, genre:"Action", duration:"2h 32m",
        poster:"https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/nMKdUUepR0i5zn0y1T4CejMntit.jpg",
        trailer:"EXeTwQWrcwY",
        desc:"Batman raises the stakes in his war on crime. The Joker unleashes anarchy on Gotham, forcing Batman to question everything he believes."
    },
    {
        id:3, title:"Interstellar", rating:8.7, year:2014, genre:"Sci-Fi", duration:"2h 49m",
        poster:"https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        trailer:"zSWdZVtXT7E",
        desc:"A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival through dimensions of time and gravity."
    },
    {
        id:4, title:"Parasite", rating:8.5, year:2019, genre:"Drama", duration:"2h 12m",
        poster:"https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/TU9NIjwzjoKPwQHoHshkFcQUCG.jpg",
        trailer:"5xH0HfJHsaY",
        desc:"Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan."
    },
    {
        id:5, title:"The Matrix", rating:8.7, year:1999, genre:"Sci-Fi", duration:"2h 16m",
        poster:"https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/fNG7i7RqMErkcqhohV2a6cV1Ehy.jpg",
        trailer:"vKQi3bBA1y8",
        desc:"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
    },
    {
        id:6, title:"Avengers: Endgame", rating:8.4, year:2019, genre:"Action", duration:"3h 1m",
        poster:"https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
        trailer:"TcMBFSGVi1c",
        desc:"After the devastating events of Infinity War, the universe is in ruins. The Avengers assemble once more to reverse Thanos's actions."
    },
    {
        id:7, title:"Joker", rating:8.4, year:2019, genre:"Drama", duration:"2h 2m",
        poster:"https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/n6bUvigpRFqSwmPp1ZIr5QKJ1Qd.jpg",
        trailer:"zAGVQLHvwOY",
        desc:"In Gotham City, mentally troubled comedian Arthur Fleck embarks on a downward spiral of revolution and crime, becoming the iconic villain Joker."
    },
    {
        id:8, title:"Get Out", rating:7.7, year:2017, genre:"Horror", duration:"1h 44m",
        poster:"https://image.tmdb.org/t/p/w500/tFXcEccSQMf3lfhfXKSU9iRBpa3.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/rLqrSBj8oXDz9HNfCVMWXfRKMXi.jpg",
        trailer:"DzfpyUB60YY",
        desc:"A young African-American visits his white girlfriend's parents for the weekend. The more time he spends there, the more disturbing secrets emerge."
    },
    {
        id:9, title:"La La Land", rating:8.0, year:2016, genre:"Romance", duration:"2h 8m",
        poster:"https://image.tmdb.org/t/p/w500/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/nadTnRbMdDnMJPjAJkgBZRXBWYw.jpg",
        trailer:"0pdqf4P9MB8",
        desc:"While navigating their careers in Los Angeles, a pianist and an aspiring actress fall in love while attempting to reconcile their aspirations for the future."
    },
    {
        id:10, title:"Dune", rating:8.0, year:2021, genre:"Sci-Fi", duration:"2h 35m",
        poster:"https://image.tmdb.org/t/p/w500/d5NXSklpcvkCgnpLIOkpTHFhhis.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/iopYFB1b6Bh7FWZh3onQhph1sih.jpg",
        trailer:"8g18jFHCLXk",
        desc:"A noble family becomes embroiled in a war for a dangerous desert planet's spice supply — the most valuable commodity in the known universe."
    },
    {
        id:11, title:"Everything Everywhere", rating:7.8, year:2022, genre:"Comedy", duration:"2h 19m",
        poster:"https://image.tmdb.org/t/p/w500/w3LxiVYdWWRvEVdn5RYq6jIqkb1.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/feSiISwgEpVzR1v3zv2n2AU4ANJ.jpg",
        trailer:"wxN1T1uxQ2g",
        desc:"A middle-aged Chinese immigrant is swept up in an insane adventure in which she alone can save the world by exploring other universes."
    },
    {
        id:12, title:"Top Gun: Maverick", rating:8.3, year:2022, genre:"Action", duration:"2h 11m",
        poster:"https://image.tmdb.org/t/p/w500/62HCnUTHjWty9vaj7KMEXkQGzbH.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/14QbnygCuTO0vl7CAFmPf1fgZfV.jpg",
        trailer:"qSqVVswa420",
        desc:"After more than thirty years of service, Pete 'Maverick' Mitchell must confront his past while training the best of the best for a dangerous mission."
    },
    {
        id:13, title:"Oppenheimer", rating:8.6, year:2023, genre:"Drama", duration:"3h 0m",
        poster:"https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/rLb2cwF3Pazuxaj0sRXQ037tGI1.jpg",
        trailer:"uYPbbksJxIg",
        desc:"The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb during World War II."
    },
    {
        id:14, title:"Barbie", rating:7.0, year:2023, genre:"Comedy", duration:"1h 54m",
        poster:"https://image.tmdb.org/t/p/w500/iuFNMS8vlbhg4NWYJEzKtZhGVCx.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/ctMserH8g2SeOAnCw5gFjdQF8mo.jpg",
        trailer:"pBk4NYhWNMM",
        desc:"Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land — until the day they go to the real world."
    },
    {
        id:15, title:"Poor Things", rating:8.0, year:2023, genre:"Drama", duration:"2h 21m",
        poster:"https://image.tmdb.org/t/p/w500/kCGlIMHnOm8JPXIbpGraham17d3x.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/bQmRiQ5WBRPKTxFGjXyTGCOBB07.jpg",
        trailer:"RlbR5N6veqw",
        desc:"The incredible tale about the fantastical evolution of Bella Baxter, a young woman brought back to life by the brilliant and unorthodox scientist Dr. Godwin Baxter."
    },
    {
        id:16, title:"Killers of the Flower Moon", rating:7.7, year:2023, genre:"Drama", duration:"3h 26m",
        poster:"https://image.tmdb.org/t/p/w500/dB6jBDcT81n5zSpiHFJN9jMDFiK.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/1X7vow16X7CnCoexXh4H4F2yDJv.jpg",
        trailer:"EP34Yoxs3FQ",
        desc:"Members of the Osage Nation are murdered under mysterious circumstances in 1920s Oklahoma, sparking a major FBI investigation."
    },
    {
        id:17, title:"Past Lives", rating:7.9, year:2023, genre:"Romance", duration:"1h 46m",
        poster:"https://image.tmdb.org/t/p/w500/k3waqVXSnCMSKTfQaUdNcPqFBVm.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/gMJngTNfaqCSCqGD4y8lVMZXyfI.jpg",
        trailer:"kA7-unhT1XE",
        desc:"Nora and Hae Sung, two childhood friends, are separated when Nora's family emigrates from South Korea. Decades later, they find each other across the world."
    },
    {
        id:18, title:"Saltburn", rating:7.1, year:2023, genre:"Thriller", duration:"2h 11m",
        poster:"https://image.tmdb.org/t/p/w500/qitIziKBNPuRGSWaIGMzBNRr5vc.jpg",
        backdrop:"https://image.tmdb.org/t/p/original/qDRep06aPSBBL1CIUPqyBUKwFrJ.jpg",
        trailer:"AnR7sfIhTbc",
        desc:"A student at Oxford University finds himself drawn into the world of a charming and aristocratic classmate who invites him to his sprawling family estate."
    },
];

// Hero featured movies with full backdrop
const HERO_MOVIES = [
    { id:13, backdrop:"https://image.tmdb.org/t/p/original/rLb2cwF3Pazuxaj0sRXQ037tGI1.jpg" },
    { id:3,  backdrop:"https://image.tmdb.org/t/p/original/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg" },
    { id:2,  backdrop:"https://image.tmdb.org/t/p/original/nMKdUUepR0i5zn0y1T4CejMntit.jpg" },
    { id:1,  backdrop:"https://image.tmdb.org/t/p/original/s3TBrRGB1iav7gFOCNx3H31MoES.jpg" },
];

const SAMPLE_REVIEWS = [
    { movie:"Inception",       stars:"★★★★★", text:"Mind-bending masterpiece. Christopher Nolan at his absolute best — every rewatch reveals new layers.", author:"Alex M.",   date:"2 days ago" },
    { movie:"The Dark Knight", stars:"★★★★★", text:"Heath Ledger's Joker is one of cinema's greatest performances. This film redefined the superhero genre.", author:"Sarah K.",  date:"5 days ago" },
    { movie:"Parasite",        stars:"★★★★☆", text:"Bong Joon-ho crafted a stunning social commentary wrapped in pure thriller. The twist still hits hard.", author:"James L.",  date:"1 week ago" },
    { movie:"Interstellar",    stars:"★★★★★", text:"Hans Zimmer's score combined with the visuals makes this an experience like no other. A true epic.", author:"Priya N.",  date:"2 weeks ago" },
    { movie:"Oppenheimer",     stars:"★★★★★", text:"Cillian Murphy delivers a career-defining performance. Three hours felt like thirty minutes.", author:"Michael T.", date:"3 weeks ago" },
    { movie:"Past Lives",      stars:"★★★★☆", text:"Quietly devastating. A film about roads not taken and how they haunt us. Beautifully acted.", author:"Emma R.",   date:"1 month ago" },
];

// ═══════════════════════════════════════════════════════
// DOM READY
// ═══════════════════════════════════════════════════════
document.addEventListener('DOMContentLoaded', function () {

    loadUserData();
    setupHeader();
    setupDropdown();
    setupSearch();
    setupLogout();

    // Render grids
    renderMovieGrid('trendingGrid',  shuffle(MOVIES).slice(0,6));
    renderMovieGrid('continueGrid',  MOVIES.slice(6,12));
    renderMovieGrid('topGrid',       [...MOVIES].sort((a,b)=>b.rating-a.rating).slice(0,6));
    renderReviews();

    // Hero
    setupHero();

    // Modals
    setupInfoModal();
    setupVideoPlayer();

    // Wishlist heart
    setupWishlist();
});

// ─── Header scroll ────────────────────────────────────────────────────────
function setupHeader() {
    const h = document.getElementById('mainHeader');
    window.addEventListener('scroll', ()=> h.classList.toggle('solid', window.scrollY > 60), { passive:true });
}

// ─── Dropdown ────────────────────────────────────────────────────────────
function setupDropdown() {
    const btn = document.getElementById('avatarBtn');
    const dd  = document.getElementById('dropdown');
    btn.addEventListener('click', e=>{ e.stopPropagation(); dd.classList.toggle('open'); });
    document.addEventListener('click', ()=> dd.classList.remove('open'));
}

// ─── Search ───────────────────────────────────────────────────────────────
function setupSearch() {
    const toggle = document.getElementById('searchToggle');
    const box    = document.getElementById('searchBox');
    const close  = document.getElementById('searchClose');
    const input  = document.getElementById('searchInput');
    toggle.addEventListener('click', ()=>{ box.classList.add('open'); input.focus(); });
    close.addEventListener('click',  ()=>{ box.classList.remove('open'); input.value=''; });
    document.addEventListener('keydown', e=>{ if(e.key==='Escape') box.classList.remove('open'); });
}

// ─── Logout ───────────────────────────────────────────────────────────────
function setupLogout() {
    document.getElementById('logoutBtn').addEventListener('click', ()=>{
        fetch('logout',{method:'POST',credentials:'include'})
            .then(()=> window.location.href='login.html')
            .catch(()=> window.location.href='login.html');
    });
}

// ─── User data ────────────────────────────────────────────────────────────
function loadUserData() {
    fetch('user-data',{method:'GET',credentials:'include'})
        .then(r=>{ if(!r.ok) throw 0; return r.json(); })
        .then(u=>{
            const name    = u.fullName||u.name||'there';
            const initial = name.charAt(0).toUpperCase();
            document.getElementById('avatarBtn').textContent      = initial;
            document.getElementById('dropdownAvatar').textContent = initial;
            document.getElementById('dropdownName').textContent   = name;
            document.getElementById('dropdownEmail').textContent  = u.email||'';
            document.getElementById('welcomeName').textContent    = name.split(' ')[0];
        }).catch(()=>{});
}

// ═══════════════════════════════════════════════════════
// HERO  (real backdrop image + auto-rotate)
// ═══════════════════════════════════════════════════════
let heroIdx = 0, heroTimer = null;

function setupHero() {
    // show first hero immediately
    applyHero(0, true);

    const dots = document.querySelectorAll('.hero-dots .dot');
    dots.forEach((d,i)=> d.addEventListener('click',()=>{ heroIdx=i; applyHero(i); resetHeroTimer(); }));
    resetHeroTimer();

    // Hero "More Info"
    document.getElementById('heroMoreInfo').addEventListener('click',()=> openInfoModal(HERO_MOVIES[heroIdx].id));
    // Hero "Watch Now"
    document.getElementById('heroWatchNow').addEventListener('click',()=> openPlayer(HERO_MOVIES[heroIdx].id));
}

function resetHeroTimer() {
    clearInterval(heroTimer);
    heroTimer = setInterval(()=>{ heroIdx=(heroIdx+1)%HERO_MOVIES.length; applyHero(heroIdx); }, 7000);
}

function applyHero(idx, immediate) {
    const hm      = HERO_MOVIES[idx];
    const movie   = MOVIES.find(m=>m.id===hm.id);
    const bgEl    = document.getElementById('heroBgImage');
    const titleEl = document.getElementById('heroTitle');
    const tagsEl  = document.getElementById('heroTags');
    const descEl  = document.getElementById('heroDesc');
    const dots    = document.querySelectorAll('.hero-dots .dot');

    const transition = immediate ? 'none' : 'opacity 0.6s ease';

    // Fade out content
    [titleEl, tagsEl, descEl].forEach(el=>{
        el.style.transition = immediate ? 'none' : 'opacity 0.4s, transform 0.4s';
        el.style.opacity='0'; el.style.transform='translateY(12px)';
    });

    // Swap background
    bgEl.style.transition = transition;
    bgEl.style.opacity = '0';

    setTimeout(()=>{
        bgEl.style.backgroundImage = `url('${hm.backdrop}')`;
        bgEl.style.transition = 'opacity 0.8s ease';
        bgEl.style.opacity = '1';

        titleEl.textContent = movie.title;
        tagsEl.innerHTML = `
            <span class="tag tag-gold">⭐ ${movie.rating}</span>
            <span class="tag">${movie.year}</span>
            <span class="tag tag-red">${movie.genre}</span>
            <span class="tag">${movie.duration}</span>`;
        descEl.textContent = movie.desc;

        [titleEl, tagsEl, descEl].forEach(el=>{
            el.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            el.style.opacity='1'; el.style.transform='translateY(0)';
        });

        dots.forEach((d,i)=> d.classList.toggle('active', i===idx));
    }, immediate ? 0 : 350);
}

// ═══════════════════════════════════════════════════════
// MOVIE GRID  (real poster images)
// ═══════════════════════════════════════════════════════
function renderMovieGrid(containerId, movies) {
    const el = document.getElementById(containerId);
    if(!el) return;
    el.innerHTML = movies.map(m=>`
        <div class="movie-card" data-id="${m.id}" role="button" tabindex="0" aria-label="${m.title}">
            <div class="movie-card-face">
                <img src="${m.poster}"
                     alt="${m.title}"
                     loading="lazy"
                     onerror="this.style.display='none';this.parentNode.classList.add('no-img')">
            </div>
            <div class="movie-card-body">
                <div class="movie-card-title">${m.title}</div>
                <div class="movie-card-foot">
                    <span class="mc-rating">⭐ ${m.rating}</span>
                    <span class="mc-genre">${m.genre}</span>
                </div>
            </div>
            <div class="movie-card-overlay">
                <button class="ov-play" data-id="${m.id}" aria-label="Watch ${m.title}">▶</button>
                <span class="ov-info" data-id="${m.id}">More Info</span>
            </div>
        </div>`).join('');

    // Events
    el.querySelectorAll('.movie-card').forEach(card=>{
        const id = parseInt(card.dataset.id);
        card.addEventListener('click', ()=> openInfoModal(id));
        card.addEventListener('keydown', e=>{ if(e.key==='Enter') openInfoModal(id); });
    });
    el.querySelectorAll('.ov-play').forEach(btn=>{
        btn.addEventListener('click', e=>{ e.stopPropagation(); openPlayer(parseInt(btn.dataset.id)); });
    });
    el.querySelectorAll('.ov-info').forEach(btn=>{
        btn.addEventListener('click', e=>{ e.stopPropagation(); openInfoModal(parseInt(btn.dataset.id)); });
    });
}

// ─── Reviews ──────────────────────────────────────────────────────────────
function renderReviews() {
    const el = document.getElementById('reviewsGrid');
    if(!el) return;
    el.innerHTML = SAMPLE_REVIEWS.map(r=>`
        <div class="review-card">
            <div class="rc-top">
                <div class="rc-poster">🎬</div>
                <div>
                    <div class="rc-movie">${r.movie}</div>
                    <div class="rc-stars">${r.stars}</div>
                </div>
            </div>
            <p class="rc-text">"${r.text}"</p>
            <div class="rc-foot">
                <span class="rc-author">${r.author}</span>
                <span class="rc-date">${r.date}</span>
            </div>
        </div>`).join('');
}

// ═══════════════════════════════════════════════════════
// INFO MODAL  (movie detail + review form)
// ═══════════════════════════════════════════════════════
let modalRating = 0;

function setupInfoModal() {
    document.getElementById('modalClose').addEventListener('click', closeInfoModal);
    document.getElementById('modalBackdrop').addEventListener('click', function(e){
        if(e.target===this) closeInfoModal();
    });

    // Star rating
    document.querySelectorAll('.mstar').forEach(star=>{
        star.addEventListener('mouseover', function(){
            const v=+this.dataset.v;
            document.querySelectorAll('.mstar').forEach(s=> s.classList.toggle('on',+s.dataset.v<=v));
        });
        star.addEventListener('mouseout', function(){
            document.querySelectorAll('.mstar').forEach(s=> s.classList.toggle('on',+s.dataset.v<=modalRating));
        });
        star.addEventListener('click', function(){
            modalRating=+this.dataset.v;
        });
    });

    document.getElementById('modalSubmitReview').addEventListener('click', ()=>{
        const text = document.getElementById('modalReviewText').value.trim();
        if(!modalRating){ alert('Please select a star rating.'); return; }
        if(!text){ alert('Please write your review.'); return; }
        document.getElementById('modalReviewSuccess').classList.add('show');
        document.getElementById('modalReviewText').value='';
        modalRating=0;
        document.querySelectorAll('.mstar').forEach(s=>s.classList.remove('on'));
        setTimeout(()=> document.getElementById('modalReviewSuccess').classList.remove('show'), 4000);
    });

    // Watch btn inside modal
    document.getElementById('modalWatchBtn').addEventListener('click', ()=>{
        const id = parseInt(document.getElementById('movieModal').dataset.movieId||0);
        closeInfoModal();
        if(id) openPlayer(id);
    });
}

function openInfoModal(movieId) {
    const m = MOVIES.find(x=>x.id===movieId);
    if(!m) return;

    document.getElementById('movieModal').dataset.movieId = movieId;
    document.getElementById('modalPoster').innerHTML =
        `<img src="${m.poster}" alt="${m.title}" style="width:100%;height:100%;object-fit:cover;border-radius:16px 0 0 16px;">`;
    document.getElementById('modalTitle').textContent    = m.title;
    document.getElementById('modalDescText').textContent = m.desc;
    document.getElementById('modalTags').innerHTML =
        `<span class="tag tag-gold">⭐ ${m.rating}</span>
         <span class="tag">${m.year}</span>
         <span class="tag tag-red">${m.genre}</span>
         <span class="tag">${m.duration}</span>`;

    // reset review form
    document.getElementById('modalReviewText').value='';
    modalRating=0;
    document.querySelectorAll('.mstar').forEach(s=>s.classList.remove('on'));
    document.getElementById('modalReviewSuccess').classList.remove('show');

    document.getElementById('modalBackdrop').classList.add('open');
    document.body.style.overflow='hidden';
}
window.openInfoModal = openInfoModal;

function closeInfoModal() {
    document.getElementById('modalBackdrop').classList.remove('open');
    document.body.style.overflow='';
}

// ═══════════════════════════════════════════════════════
// VIDEO PLAYER MODAL  (YouTube embed + custom UI)
// ═══════════════════════════════════════════════════════
function setupVideoPlayer() {
    document.getElementById('playerClose').addEventListener('click', closePlayer);
    document.getElementById('playerBackdrop').addEventListener('click', function(e){
        if(e.target===this) closePlayer();
    });
    document.addEventListener('keydown', e=>{ if(e.key==='Escape') closePlayer(); });
}

function openPlayer(movieId) {
    const m = MOVIES.find(x=>x.id===movieId);
    if(!m) return;

    // Show loading state
    const backdrop = document.getElementById('playerBackdrop');
    const frame    = document.getElementById('playerFrame');
    const title    = document.getElementById('playerTitle');
    const meta     = document.getElementById('playerMeta');
    const loader   = document.getElementById('playerLoader');

    title.textContent = m.title;
    meta.textContent  = `${m.year} · ${m.genre} · ${m.duration}`;

    // Show loader first
    loader.style.display = 'flex';
    frame.style.opacity  = '0';
    frame.src = '';

    backdrop.classList.add('open');
    document.body.style.overflow = 'hidden';

    // Load YouTube embed with autoplay
    setTimeout(()=>{
        frame.src = `https://www.youtube.com/embed/${m.trailer}?autoplay=1&rel=0&modestbranding=1&color=white`;
        frame.onload = ()=>{
            loader.style.display = 'none';
            frame.style.opacity  = '1';
        };
    }, 400);
}
window.openPlayer = openPlayer;

function closePlayer() {
    const backdrop = document.getElementById('playerBackdrop');
    backdrop.classList.remove('open');
    // Stop video immediately
    document.getElementById('playerFrame').src = '';
    document.body.style.overflow='';
}

// ─── Wishlist ─────────────────────────────────────────────────────────────
function setupWishlist() {
    const btn = document.getElementById('heroWishlist');
    let on = false;
    btn.addEventListener('click',()=>{
        on=!on;
        btn.innerHTML = on
            ? '<svg viewBox="0 0 24 24" fill="#e50914" stroke="#e50914" stroke-width="2"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg>'
            : '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg>';
        btn.style.background = on ? 'rgba(229,9,20,0.3)' : '';
        btn.style.borderColor = on ? '#e50914' : '';
    });
}

// ─── Utility ──────────────────────────────────────────────────────────────
function shuffle(arr) {
    const a=[...arr];
    for(let i=a.length-1;i>0;i--){const j=Math.floor(Math.random()*(i+1));[a[i],a[j]]=[a[j],a[i]];}
    return a;
}
