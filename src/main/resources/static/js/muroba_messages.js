// document.querySelector('.chat[data-chat=person2]').classList.add('active-chat');
// document.querySelector('.person[data-chat=person2]').classList.add('active');

const chatHistory = document.querySelector(".chat")

let friends = {
    list: document.querySelector('ul.people'),
    all: document.querySelectorAll('.left .person'),
    name: ''
};

friends.all.forEach((f) => {
    f.addEventListener('mousedown', (f) => {
        if (!f.classList.contains('active')) {
            // friends.list.querySelector('.active').classList.remove('active');
            // f.classList.add('active');
            showRecentChat();
        }
    });
});
// document.querySelector('#navbar-messages').addEventListener('click', () => {
//     showRecentChat();
// })

function showRecentChat() {

    // ajax

    setTimeout(function () {
        console.log(chatHistory)
        chatHistory.scrollTo({ top: chatHistory.scrollHeight, behavior: 'smooth' })
    }, 100);
}