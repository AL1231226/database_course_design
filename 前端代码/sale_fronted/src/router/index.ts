import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/hello',
      name: 'hello',
      component: () => import('../customer/hello.vue')
    },
    {
      path: '/customer',
      component: () => import('../customer/CustomerLayout.vue'),
      children: [
        // 子路由：products, cart, orders 等
      ]
    }
  ]
})

export default router

