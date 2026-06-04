import axios from 'axios'

const http = axios.create({ baseURL: '/api' })

export function getProducts() {
  return http.get('/products')              // GET /api/products
}

export function getProduct(id) {
  return http.get(`/products/${id}`)        // GET /api/products/{id}
}

export function getLowStock(threshold = 50) {
  return http.get('/products/low-stock', { params: { threshold } })  // GET /api/products/low-stock?threshold=50
}

export function addProduct(product) {
  return http.post('/products', product)     // POST /api/products
}

export function updateProduct(id, product) {
  return http.put(`/products/${id}`, product) // PUT /api/products/{id}
}

export function deleteProduct(id) {
  return http.delete(`/products/${id}`)       // DELETE /api/products/{id}
}

// ---------- Customers ----------
export function getCustomers() {
  return http.get('/customers')               // GET /api/customers
}

// ---------- Orders ----------
export function createOrder(order) {
  return http.post('/orders', order)           // POST /api/orders
}
