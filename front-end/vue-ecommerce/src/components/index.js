import XtxSku from "./XtxSku/index.vue";
import GoodsItem from './GoodsItem/index.vue';

export const componentPlugin = {
  install(app) {
    app.component("XtxSku", XtxSku);
    app.component("GoodsItem", GoodsItem);
  },
};
