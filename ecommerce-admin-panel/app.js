const API_BASE = 'http://localhost:8080';
let currentUser = null;
let currentToken = localStorage.getItem('jwt');

// --- Initialization ---
document.addEventListener('DOMContentLoaded', () => {
    if (currentToken) {
        showMainScreen();
    }
    setupEventListeners();
});

// --- Auth Functions ---
async function login(username, password) {
    try {
        const response = await fetch(`${API_BASE}/api/v1/users/auth/signin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) throw new Error('Invalid credentials');

        const data = await response.json();
        currentToken = data.token;
        currentUser = data;
        localStorage.setItem('jwt', currentToken);
        showMainScreen();
    } catch (error) {
        document.getElementById('login-error').textContent = error.message;
    }
}

function logout() {
    localStorage.removeItem('jwt');
    currentToken = null;
    document.getElementById('main-screen').classList.remove('active');
    document.getElementById('login-screen').classList.add('active');
}

// --- Screen Management ---
function showMainScreen() {
    document.getElementById('login-screen').classList.remove('active');
    document.getElementById('main-screen').classList.add('active');
    document.getElementById('user-display-name').textContent = currentUser?.username || 'Admin';
    loadDashboardData();
}

function switchView(viewName) {
    document.querySelectorAll('.view').forEach(v => v.classList.remove('active'));
    document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
    
    document.getElementById(`${viewName}-view`).classList.add('active');
    document.querySelector(`[data-target="${viewName}"]`).classList.add('active');
    document.getElementById('page-title').textContent = viewName.charAt(0).toUpperCase() + viewName.slice(1);

    if (viewName === 'products') fetchProducts();
    if (viewName === 'categories') fetchCategories();
}

// --- Product CRUD ---
async function fetchProducts() {
    try {
        const res = await fetch(`${API_BASE}/api/v1/products/admin/allProducts`, {
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });
        const products = await res.json();
        const tbody = document.querySelector('#products-table tbody');
        tbody.innerHTML = products.map(p => `
            <tr>
                <td>${p.id}</td>
                <td>${p.productName}</td>
                <td>${p.productDescription}</td>
                <td>$${p.productPrice}</td>
                <td>
                    <button class="btn btn-ghost" onclick="editProduct(${p.id})"><i class="fas fa-edit"></i></button>
                    <button class="btn btn-ghost btn-danger" onclick="deleteProduct(${p.id})"><i class="fas fa-trash"></i></button>
                    ${!p.approved ? `<button class="btn btn-success" onclick="approveProduct(${p.id})">Approve</button>` : ''}
                </td>
            </tr>
        `).join('');
        document.getElementById('stat-products-count').textContent = products.length;
    } catch (err) { console.error('Fetch products error:', err); }
}

async function saveProduct(e) {
    e.preventDefault();
    const id = document.getElementById('product-id').value;
    const data = {
        productName: document.getElementById('p-name').value,
        productDescription: document.getElementById('p-desc').value,
        productPrice: document.getElementById('p-price').value
    };

    const url = id ? `${API_BASE}/api/v1/products/admin/update/${id}` : `${API_BASE}/api/v1/products/admin/create/1`;
    const method = id ? 'PUT' : 'POST';

    try {
        const res = await fetch(url, {
            method: method,
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${currentToken}`
            },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            hideModal('product-modal');
            fetchProducts();
        }
    } catch (err) { console.error('Save product error:', err); }
}

async function deleteProduct(id) {
    if (!confirm('Are you sure?')) return;
    try {
        await fetch(`${API_BASE}/api/v1/products/admin/delete/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });
        fetchProducts();
    } catch (err) { console.error('Delete product error:', err); }
}

async function approveProduct(id) {
    try {
        await fetch(`${API_BASE}/api/v1/products/admin/Product/${id}/approve`, {
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });
        fetchProducts();
    } catch (err) { console.error('Approve product error:', err); }
}

// --- Category CRUD ---
async function fetchCategories() {
    try {
        const res = await fetch(`${API_BASE}/api/v1/categories/admin/alladvertisements`, {
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });
        const items = await res.json();
        const tbody = document.querySelector('#categories-table tbody');
        tbody.innerHTML = items.map(c => `
            <tr>
                <td>${c.id}</td>
                <td>${c.advertisementName}</td>
                <td>${c.advertisementDescription}</td>
                <td>${c.targetLink}</td>
                <td>
                    <button class="btn btn-ghost" onclick="editCategory(${c.id})"><i class="fas fa-edit"></i></button>
                    <button class="btn btn-ghost btn-danger" onclick="deleteCategory(${c.id})"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        `).join('');
        document.getElementById('stat-categories-count').textContent = items.length;
    } catch (err) { console.error('Fetch categories error:', err); }
}

async function saveCategory(e) {
    e.preventDefault();
    const id = document.getElementById('category-id').value;
    const data = {
        advertisementName: document.getElementById('c-name').value,
        advertisementDescription: document.getElementById('c-desc').value,
        targetLink: document.getElementById('c-target').value
    };

    const url = id ? `${API_BASE}/api/v1/categories/admin/update/${id}` : `${API_BASE}/api/v1/categories/admin/create/1`;
    const method = id ? 'PUT' : 'POST';

    try {
        const res = await fetch(url, {
            method: method,
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${currentToken}`
            },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            hideModal('category-modal');
            fetchCategories();
        }
    } catch (err) { console.error('Save category error:', err); }
}

async function deleteCategory(id) {
    if (!confirm('Are you sure?')) return;
    try {
        await fetch(`${API_BASE}/api/v1/categories/admin/delete/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });
        fetchCategories();
    } catch (err) { console.error('Delete category error:', err); }
}

// --- UI Helpers ---
function setupEventListeners() {
    document.getElementById('login-form').addEventListener('submit', (e) => {
        e.preventDefault();
        login(document.getElementById('username').value, document.getElementById('password').value);
    });

    document.getElementById('logout-btn').addEventListener('click', logout);

    document.querySelectorAll('.nav-item').forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            switchView(item.dataset.target);
        });
    });

    document.getElementById('product-form').addEventListener('submit', saveProduct);
    document.getElementById('category-form').addEventListener('submit', saveCategory);
}

function showModal(id) {
    document.getElementById(id).style.display = 'flex';
}

function hideModal(id) {
    document.getElementById(id).style.display = 'none';
    const form = document.querySelector(`#${id} form`);
    if (form) form.reset();
    const idField = document.querySelector(`#${id} input[type="hidden"]`);
    if (idField) idField.value = '';
}

async function loadDashboardData() {
    // Analytics is empty, so we'll just trigger counts from individual fetch calls
    fetchProducts();
    fetchCategories();
}

// Exposed to global for onclick handlers
window.showModal = showModal;
window.hideModal = hideModal;
window.editProduct = async (id) => {
    const res = await fetch(`${API_BASE}/api/v1/products/admin/Product/${id}`, {
        headers: { 'Authorization': `Bearer ${currentToken}` }
    });
    const p = await res.json();
    document.getElementById('product-id').value = p.id;
    document.getElementById('p-name').value = p.productName;
    document.getElementById('p-desc').value = p.productDescription;
    document.getElementById('p-price').value = p.productPrice;
    showModal('product-modal');
};
window.editCategory = async (id) => {
    const res = await fetch(`${API_BASE}/api/v1/categories/admin/alladvertisements/${id}`, {
        headers: { 'Authorization': `Bearer ${currentToken}` }
    });
    const c = await res.json();
    document.getElementById('category-id').value = c.id;
    document.getElementById('c-name').value = c.advertisementName;
    document.getElementById('c-desc').value = c.advertisementDescription;
    document.getElementById('c-target').value = c.targetLink;
    showModal('category-modal');
};
window.deleteProduct = deleteProduct;
window.deleteCategory = deleteCategory;
window.approveProduct = approveProduct;
