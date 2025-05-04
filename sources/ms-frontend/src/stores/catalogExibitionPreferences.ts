import { defineStore } from "pinia";
import { type Ref, ref } from "vue";

enum ExibitionMode {
  LISTING,
  BOXES,
}

export const useExibitionModeStore = defineStore("exibitionMode", () => {
  const exibitionMode: Ref<ExibitionMode> = ref(ExibitionMode.LISTING);

  function setListing() {
    exibitionMode.value = ExibitionMode.LISTING;
  }

  function setBoxes() {
    exibitionMode.value = ExibitionMode.BOXES;
  }

  return { exibitionMode, setListing, setBoxes };
});
