const handleTabSign = () => {
  let h2s = document.querySelectorAll(".tab .sign");
  let forms = document.querySelectorAll(".form_wrap .form");
  h2s.forEach((h2) => {
    h2.addEventListener("click", () => {
      h2s.forEach((h2) => {
        h2.classList.remove("active");
      });
      h2.classList.add("active");
      forms.forEach((form) => {
        form.classList.remove("active");
      });
      let idTab = h2.getAttribute("data-tab");
      document.querySelector(`.form_${idTab}`).classList.add("active");
    });
  });
};
handleTabSign();
