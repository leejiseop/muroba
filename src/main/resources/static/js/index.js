document.getElementById("signup-button").addEventListener("click", function () {
  alert("hi");

  const email = document.getElementById("signup-email").value
  const password = document.getElementById("signup-password").value
  const nickname = document.getElementById("signup-nickname").value
  const country = document.getElementById("signup-country").value

  console.log(email, password, nickname, country)
  signup();
});

function signup() {
  alert("hihihi")
}