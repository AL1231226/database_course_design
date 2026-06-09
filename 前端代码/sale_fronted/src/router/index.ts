import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      redirect: "/customer",
    },
    {
      path: "/customer",
      component: () => import("../customer/CustomerLayout.vue"),
      redirect: "/customer/products",
      children: [
        {
          path: "products",
          name: "customer-products",
          component: () => import("../customer/ProductList.vue"),
        },
        {
          path: "products/category/:code",
          name: "customer-category",
          component: () => import("../customer/CategoryProducts.vue"),
        },
        {
          path: "cart",
          name: "customer-cart",
          component: () => import("../customer/ShoppingCart.vue"),
        },
        {
          path: "orders",
          name: "customer-orders",
          component: () => import("../customer/OrderHistory.vue"),
        },
      ],
    },
    {
      path: "/admin",
      component: () => import("../admin/AdminLayout.vue"),
      redirect: "/admin/dashboard",
      children: [
        {
          path: "dashboard",
          name: "admin-dashboard",
          component: () => import("../admin/Dashboard.vue"),
        },
        {
          path: "orders",
          name: "admin-orders",
          component: () => import("../admin/OrderManage.vue"),
        },
        {
          path: "inventory",
          name: "admin-inventory",
          component: () => import("../admin/InventoryManage.vue"),
        },
        {
          path: "customers",
          name: "admin-customers",
          component: () => import("../admin/CustomerManage.vue"),
        },
        {
          path: "manufacturers",
          name: "admin-manufacturers",
          component: () => import("../admin/ManufacturerManage.vue"),
        },
      ],
    },
  ],
});

export default router;
