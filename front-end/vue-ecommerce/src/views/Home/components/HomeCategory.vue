<script setup>
import { ref, onMounted } from "vue";
import { getHeadAPI } from '@/apis/home.js';

const headList = ref([]);

const getHeadList = async () => {
  const res = await getHeadAPI()
  headList.value = res.result;
}

onMounted(() => {
  getHeadList()
})
</script>

<template>
  <div class="home-category">
    <ul class="menu">
      <li v-for="item in headList" :key="item.category.id">
        <RouterLink :to="`/category/${item.category.id}`">{{ item.category.name }}</RouterLink>
        <RouterLink v-for="i in item.category.sub_categories.slice(0, 2)" :key="i.id" to="/">{{
          i.name
        }}</RouterLink>
        <!-- 彈層layer位置 -->
        <div class="layer">
          <h4>分類推薦</h4>
          <ul>
            <li v-for="i in item.goods" :key="i.id">
              <RouterLink :to="`/detail/${i.id}`">
                <img :src="i.image_url" alt="" />
                <div class="info">
                  <p class="name ellipsis-2">
                    {{ i.name }}
                  </p>
                  <p class="desc ellipsis">{{ i.description }}</p>
                  <p class="price">${{ i.price }}</p>
                </div>
              </RouterLink>
            </li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
.home-category {
  width: 250px;
  height: 500px;
  background: rgba(0, 0, 0, 0.8);
  position: relative;
  z-index: 99;

  .menu {
    li {
      padding-left: 40px;
      height: 55px;
      line-height: 55px;

      &:hover {
        background: $xtxColor;
      }

      a {
        margin-right: 4px;
        color: #fff;

        &:first-child {
          font-size: 16px;
        }
      }

      .layer {
        width: 990px;
        height: 500px;
        background: rgba(255, 255, 255, 0.8);
        position: absolute;
        left: 250px;
        top: 0;
        display: none;
        padding: 0 15px;

        h4 {
          font-size: 20px;
          font-weight: normal;
          line-height: 80px;

          small {
            font-size: 16px;
            color: #666;
          }
        }

        ul {
          display: flex;
          flex-wrap: wrap;

          li {
            width: 310px;
            height: 120px;
            margin-right: 15px;
            margin-bottom: 15px;
            padding-left: 0px;
            border: 1px solid #eee;
            border-radius: 4px;
            background: #fff;

            &:nth-child(3n) {
              margin-right: 0;
            }

            a {
              display: flex;
              width: 100%;
              height: 100%;
              align-items: center;
              padding: 10px;

              &:hover {
                background: #e3f9f4;
              }

              img {
                width: 95px;
                height: 95px;
              }

              .info {
                padding-left: 10px;
                line-height: 24px;
                overflow: hidden;

                .name {
                  font-size: 16px;
                  color: #666;
                }

                .desc {
                  color: #999;
                }

                .price {
                  margin-top: 5px;
                  font-size: 22px;
                  color: $priceColor;

                  i {
                    font-size: 16px;
                  }
                }
              }
            }
          }
        }
      }

      // 關鍵樣式  hover狀態下的layer盒子變成block
      &:hover {
        .layer {
          display: block;
        }
      }
    }
  }
}
</style>
