import GoodsItem from './GoodsItem/index.vue';

export const componentPlugin = {
  install(app) {
    app.component("GoodsItem", GoodsItem);
  },
};
