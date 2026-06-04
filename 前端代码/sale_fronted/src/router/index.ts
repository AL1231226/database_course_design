import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/customer'
    },
    {
      path: '/hello',
      name: 'hello',
      component: () => import('../customer/hello.vue')
    },
    {
      path: '/customer',
      component: () => import('../customer/CustomerLayout.vue'),
      redirect: '/customer/products',
      children: [
        {
          path: 'products',
          name: 'customer-products',
          component: () => import('../customer/ProductList.vue')
        },
        {
          path: 'cart',
          name: 'customer-cart',
          component: () => import('../customer/ShoppingCart.vue')
        },
      ]
    }
  ]
})

export default router

