async function loadQuestion() {
  const id = window.location.pathname.split("/").pop();
  const res = await fetch(`/api/questions/${id}`);
  if (res.ok) {
    const q = await res.json();
    document.getElementById("question-title").innerText =
      q.title || q.content.substring(0, 20);
    document.getElementById("question-content").innerText = q.content;
  }
}
async function loadComments() {
  const id = window.location.pathname.split("/").pop();
  const res = await fetch(`/api/answers/question/${id}`);
  if (res.ok) {
    const comments = await res.json();
    const container = document.getElementById("comments");
    container.innerHTML = "";
    comments.forEach((c) => {
      const div = document.createElement("div");
      div.className = "comment";
      div.innerHTML = `<div>${c.comment}</div>`;
      // 대댓글
      if (c.replies && c.replies.length > 0) {
        c.replies.forEach((r) => {
          const replyDiv = document.createElement("div");
          replyDiv.className = "reply";
          replyDiv.innerText = r.comment;
          div.appendChild(replyDiv);
        });
      }
      container.appendChild(div);
    });
  }
}
loadQuestion();
loadComments();
