<template>
  <div>
    <div class="header">
      <div>
        <router-link to="/">Home</router-link> | 
        <router-link to="/book">도서목록</router-link> |
        <router-link to="/about">About</router-link>
      </div>
      <router-link to="/">
        <img src="@/assets/ssafy_logo.png" id="ssafy_logo"/>
      </router-link>
      <p class="logo">도서관리</p>
    </div>
  </div>
</template>