import axios from "axios";

const api = axios.create({
  baseURL: "/api",
  timeout: 10000,
  headers: { "Content-Type": "application/json" },
});

export default {
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
