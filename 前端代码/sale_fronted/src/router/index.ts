import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/login",
      name: "login",
      component: () => import("../Login.vue"),
    },
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
          meta: { requiresAuth: true, role: 'customer' },
        },
        {
          path: "orders",
          name: "customer-orders",
          component: () => import("../customer/OrderHistory.vue"),
          meta: { requiresAuth: true, role: 'customer' },
        },
      ],
    },
    {
      path: "/admin",
      component: () => import("../admin/AdminLayout.vue"),
      redirect: "/admin/dashboard",
      meta: { requiresAuth: true, role: 'admin' },
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

// 路由守卫
router.beforeEach((to, from, next) => {
  const raw = sessionStorage.getItem('user');
  const user = raw ? JSON.parse(raw) : null;

  // 登录页不拦截
  if (to.path === '/login') return next();

  // 检查当前路由或其父路由是否要求认证
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const requiredRole = to.matched.find(record => record.meta.role)?.meta.role;

  if (requiresAuth) {
    if (!user) {
      return next('/login?redirect=' + encodeURIComponent(to.path));
    }
    if (requiredRole && user.role !== requiredRole) {
      if (user.role === 'admin') return next('/admin');
      return next('/customer');
    }
  }

  next();
});

export default router;
