
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
    location.replace('./muroba.html');
  }).fail(function (response) {
    alert("not ok")
  })
});

function signup(event) {

  let email = document.querySelector('#signup-email').value
  // let auth = document.querySelector('#email-auth').value
  let password = document.querySelector('#signup-password').value
  let nickname = document.querySelector('#signup-nickname').value
  let country = document.querySelector('#signup-country').value

  if (!email || !password || !nickname || !country) {
    alert("모든 값을 입력해주세요.");
    return; // auth = 1 이면 통과 기능 넣기
  }
  if (!isEmail(email)) {
    alert("이메일 형식을 확인해주세요.")
    return;
  }

  /*
  이메일 인증 기능 추가
  함수 하나 만들고 true 뜨면
  auth 변수에 통과값 넣기
  */

  const settings = {
    "url": `/api/members/create`,
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
    location.reload(true);
  }).fail(function (response) {
    alert("not ok signup")
  })
}

function isEmail(email_address) {
  email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
  if (!email_regex.test(email_address)) {
    return false;
  } else {
    return true;
  }
}




function checkEmailAndSendAuth(event) {

  let email = event.currentTarget.previousElementSibling.value

  if (!isEmail(email)) {
    alert('이메일 형식이 올바르지 않습니다.')
    return
  }

  const settings = {
    "url": `/api/members/email`,
    "method": "get",
    "timeout": 0,
    "headers": {
      "Content-Type": "application/json"
    },
    "data": {
      "email": email
    }
  };

  $.ajax(settings).done(function (response) {
    if (response.available) {
      sendauth(email)
      alert('인증번호가 전송되었습니다. 인증번호를 확인 후 입력해주세요.')
      document.querySelector('#email-auth').disabled = false

    } else {
      alert('중복된 이메일입니다.')
    }
  }).fail(function (response) {
    alert("not ok checkEmailAndSendAuth")
  })

}

function sendauth(email) {
  const settings = {
    "url": `/api/email/sendauth`,
    "method": "post",
    "timeout": 0,
    "headers": {
      "Content-Type": "application/json"
    },
    "data": JSON.stringify({
      "email": email
    })
  };

  $.ajax(settings).done(function (response) {
    // 인증번호 전송 후 로직
  }).fail(function (response) {
    alert("not ok sendauth")
  })
}


function checkauth(event) {

}








