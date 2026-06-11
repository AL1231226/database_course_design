import axios from "axios";

const api = axios.create({
  baseURL: "/api",
  timeout: 10000,
  headers: { "Content-Type": "application/json" },
});

// Axios 请求拦截器：自动带 token
api.interceptors.request.use((config) => {
  const raw = sessionStorage.getItem("user");
  if (raw) {
    const user = JSON.parse(raw);
    if (user.token) {
      config.headers.Authorization = `Bearer ${user.token}`;
    }
  }
  return config;
});

// Axios 响应拦截器：401 跳登录
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      sessionStorage.removeItem("user");
      // 避免在登录页也跳转
      if (window.location.pathname !== "/login") {
        window.location.href = "/login";
      }
    }
    return Promise.reject(err);
  },
);

export default {
  // 认证
  login(data) {
    return api.post("/auth/login", data);
  },
  register(data) {
    return api.post("/auth/register", data);
  },
  changePassword(data) {
    return api.put("/auth/password", data);
  },
  // 商品
  getProducts() {
    return api.get("/products");
  },
  getProduct(id) {
    return api.get(`/products/${id}`);
  },
  getLowStock(threshold = 50) {
    return api.get("/products/low-stock", { params: { threshold } });
  },
  addProduct(data) {
    return api.post("/products", data);
  },
  updateProduct(id, data) {
    return api.put(`/products/${id}`, data);
  },
  deleteProduct(id) {
    return api.delete(`/products/${id}`);
  },
  getProductsByCategory(code) {
    return api.get(`/products/category/${code}`);
  },
  getAllCategories() {
    return api.get("/products/categories");
  },

  // 客户
  getAllCustomers() {
    return api.get("/customers");
  },
  getCustomers() {
    return api.get("/customers");
  },
  getCustomer(id) {
    return api.get(`/customers/${id}`);
  },
  createCustomer(data) {
    return api.post("/customers", data);
  },
  addCustomer(data) {
    return api.post("/customers", data);
  },
  updateCustomer(id, data) {
    return api.put(`/customers/${id}`, data);
  },
  deleteCustomer(id) {
    return api.delete(`/customers/${id}`);
  },

  // 订单
  getAllOrders() {
    return api.get("/orders");
  },
  getOrders() {
    return api.get("/orders");
  },
  getOrder(id) {
    return api.get(`/orders/${id}`);
  },
  createOrder(data) {
    return api.post("/orders", data);
  },
  getUnpaidOrders() {
    return api.get("/orders/unpaid");
  },
  getUnshippedOrders() {
    return api.get("/orders/unshipped");
  },
  getCompletedOrders() {
    return api.get("/orders/completed");
  },
  confirmSupply(id) {
    return api.put(`/orders/${id}/confirm`);
  },
  updatePayment(id, data) {
    return api.put(`/orders/${id}/payment`, data);
  },
  cancelOrder(id) {
    return api.put(`/orders/${id}/cancel`);
  },
  getCancelledOrders() {
    return api.get("/orders/cancelled");
  },

  // 当前顾客的订单
  getMyOrders(tab = "all") {
    return api.get("/orders/my", { params: { tab } });
  },

  // 管理员反馈
  updateFeedback(id, data) {
    return api.put(`/orders/${id}/feedback`, data);
  },

  // 厂家
  getManufacturers() {
    return api.get("/manufacturers");
  },
  getManufacturer(id) {
    return api.get(`/manufacturers/${id}`);
  },
  addManufacturer(data) {
    return api.post("/manufacturers", data);
  },
  updateManufacturer(id, data) {
    return api.put(`/manufacturers/${id}`, data);
  },
  deleteManufacturer(id) {
    return api.delete(`/manufacturers/${id}`);
  },
};
