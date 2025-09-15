
let chatHistory = document.querySelector('.chat')

let friends = {
    list: document.querySelector('ul.people'),
    people: document.querySelectorAll('.left .person'),
}

friends.people.forEach(person => {
    person.addEventListener('mousedown', () => {
        if (!person.classList.contains('active')) {
            setAciveChat(person);
        }
    })
});

function setAciveChat(person) {

    friends.list.querySelector('.active').classList.remove('active');
    person.classList.add('active');

    // alert('yeah')
    // 새 메시지 빨간점 기능 나중에 추가하기
    // 메시지 보면 빨간점 없애기
    // 메시지 수는 안해도 될듯
    // document.querySelector("#unread_messages").innerText = "0"
    // unread_messages_num = 0
    setTimeout(function () {
        chatHistory.scrollTo({ top: chatHistory.scrollHeight, behavior: 'smooth' })
    }, 100);
}
