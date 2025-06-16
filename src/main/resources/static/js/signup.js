document
  .getElementById("signupForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const data = {
      email: document.getElementById("email").value,
      password: document.getElementById("password").value,
      passwordConfirm: document.getElementById("passwordConfirm").value,
      nickname: document.getElementById("nickname").value,
      country: document.getElementById("country").value,
    };
    const res = await fetch("/api/members/sign-up", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
    if (res.ok) {
      window.location.href = "/";
    } else {
      const msg = await res.text();
      document.getElementById("error").innerText = msg || "회원가입 실패";
    }
  });
