<template>
    <div class="header">
        <nav>
            <router-link :to="{name: 'home'}">Home</router-link> |
            <router-link :to="{name: 'book'}">도서목록</router-link> |
            <router-link :to="{name: 'about'}">About</router-link>
        </nav>
        <a href="index.html">
            <img src="@/assets/ssafy_logo.png" alt="ssafy_logo">
          </a>
        <p class="logo">도서관리</p>
    </div>
</template>

<script>
export default {
    name: 'HeaderNav',
}
</script>
