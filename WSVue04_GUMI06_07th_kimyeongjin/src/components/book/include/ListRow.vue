<template>
            <tr>
              <td>{{no}}</td>
              <td>{{isbn}}</td>
              <td><router-link :to="`biij.view?isbn=${isbn}`">{{title}}</router-link></td>
              <td>{{author}}</td>
              <td>{{price | priceFilter}}</td>
            </tr>
</template>

<script>

export default {
    name: "ListRow"
}
</script>
