// UI - codepen
// var working = false;
// $(".login").on("submit", function (e) {
//   e.preventDefault();
//   if (working) return;
//   working = true;
//   var $this = $(this),
//     $state = $this.find("button > .state");
//   $this.addClass("loading");
//   $state.html("Authenticating");
//   setTimeout(function () {
//     $this.addClass("ok");
//     $state.html("Welcome back!");
//     setTimeout(function () {
//       $state.html("Log in");
//       $this.removeClass("ok loading");
//       working = false;
//     }, 2000);
//   }, 3000);
// });

document.getElementById("login-form").addEventListener("submit", function (e) {
  e.preventDefault();
  login();
});

// 로그인
async function login() {
  const email = document.getElementById("signin-email").value;
  const password = document.getElementById("signin-password").value;

  try {
    const response = await fetch(`${origin}/api/members/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    if (response.ok) location.replace("./main.html"); // 로그인 성공 후 메인 페이지로 이동

    if (!response.ok) {
      const errorData = await response.json();
      alert("로그인에 실패했습니다.");
      location.reload(); // 로그인 실패 시 페이지 새로고침
    }

    // 응답 헤더에서 토큰 꺼내기
    // const accessToken = response.headers.get('Authorization');
    // const refreshToken = response.headers.get('RefreshToken');

    // if (accessToken && refreshToken) {
    //     localStorage.setItem('Authorization', accessToken);
    //     localStorage.setItem('RefreshToken', refreshToken);
    //     localStorage.setItem('is_logged_in', 'true');
    //     location.replace('./main.html'); // 로그인 성공 후 메인 페이지로 이동
    // } else {
    //     alert('토큰 정보가 누락되었습니다.');
    // }

    // 메인 페이지로 이동
    // location.replace("./main.html");
  } catch (error) {
    console.error(error);
    alert("서버 요청 중 오류가 발생했습니다.");
    location.reload(); // 서버 오류 시 페이지 새로고침
  }
}
