let page = 0;
let loading = false;
async function loadQuestions() {
  if (loading) return;
  loading = true;
  document.getElementById("loading").style.display = "block";
  const res = await fetch(`/api/questions?page=${page}`);
  if (res.ok) {
    const data = await res.json();
    data.content.forEach((q) => {
      const div = document.createElement("div");
      div.className = "question";
      div.onclick = () => (location.href = `/question/${q.id}`);
      div.innerHTML = `<div>${
        q.title || q.content.substring(0, 20)
      }</div><div>${q.content}</div>`;
      document.getElementById("question-list").appendChild(div);
    });
    page++;
  }
  document.getElementById("loading").style.display = "none";
  loading = false;
}
window.onscroll = function () {
  if (
    window.innerHeight + window.scrollY >= document.body.offsetHeight - 10 &&
    !loading
  ) {
    loadQuestions();
  }
};
// 첫 페이지 로드
loadQuestions();
