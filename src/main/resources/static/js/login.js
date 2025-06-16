document
  .getElementById("loginForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const data = {
      email: document.getElementById("email").value,
      password: document.getElementById("password").value,
    };
    const res = await fetch("/api/members/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
    if (res.ok) {
      const response = await res.json();
      localStorage.setItem('memberId', response.memberId);
      window.location.href = "/main";
    } else {
      const msg = await res.text();
      document.getElementById("error").innerText = msg || "로그인 실패";
    }
  });
