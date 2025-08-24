
document.getElementById("login_skip").addEventListener("click", function () {
  // e.preventDefault();

  const settings = {
    "url": `/test`,
    "method": "GET",
    "timeout": 0,
    "headers": {
      "Content-Type": "application/json"
    }
  };

  $.ajax(settings).done(function (response) {
    console.log(response)
    alert("okokok")
    location.replace('./postList.html');
  }).fail(function (response) {
    alert("not ok")
  })
});


document.getElementById("signup-button").addEventListener("click", function () {

  // e.preventDefault();

  const email = document.getElementById("signup-email").value
  const password = document.getElementById("signup-password").value
  const nickname = document.getElementById("signup-nickname").value
  const country = document.getElementById("signup-country").value

  console.log(email, password, nickname, country)

  signup(email, password, nickname, country);
});

function signup(email, password, nickname, country) {

  const settings = {
    "url": `/sign-up`,
    "method": "POST",
    "timeout": 0,
    "headers": {
      "Content-Type": "application/json"
    },
    "data": JSON.stringify({
      "email": email,
      "password": password,
      "nickname": nickname,
      "country": country
    })
  };

  $.ajax(settings).done(function (response) {
    console.log(response)
    alert("회원가입이 완료되었습니다")
  }).fail(function (response) {
    alert("not ok")
  })
}