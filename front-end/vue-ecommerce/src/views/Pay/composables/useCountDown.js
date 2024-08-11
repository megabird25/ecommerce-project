import { onUnmounted, ref } from "vue";

export const useCountDown = () => {
  let timer = null;
  const formatTime = ref(0);

  const start = (currentTime) => {
    formatTime.value = currentTime;
    timer = setInterval(() => {
      formatTime.value--;
    }, 1000);
  };

  onUnmounted(() => {
    timer && clearInterval(timer);
  });

  return {
    formatTime,
    start,
  };
};
