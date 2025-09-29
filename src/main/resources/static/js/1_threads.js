
const slice_size = 10
let slice_page = 0
let is_last = false
const contentId = '#comments-list';
const loading_button = document.querySelector('#loading-button')
const loading_button_text = document.querySelector('#loading-button-text')

history.scrollRestoration = "manual"

document.addEventListener("DOMContentLoaded", function () {
    showSlicedPosts(slice_size, slice_page)
});

loading_button.addEventListener('click', function () {
    if (is_last) {
        loading_button_text.innerText = '마지막 페이지입니다'
        loading_button.disabled = true
        return
    }
    loading_button.classList.add('loading-button-circle')
    setTimeout(() => {
        showSlicedPosts(slice_size, slice_page)
        loading_button.classList.remove('loading-button-circle')
    }, 500);
})

function open_comment(event) {



    // 클릭한곳이 게시글 내용이 아니면 return
    if (event.target.classList.contains('comment-content') == false) { return }

    let reply_list = event.currentTarget.parentNode.nextElementSibling

    // 원래 열려있었으면 - 닫는다
    if (reply_list.classList.contains('reply-list-active')) {
        reply_list.classList.remove('reply-list-active')
        reply_list.innerHTML = ''
        // 원래 닫혀있었으면 - 연다
    } else {

        // 열려있던게 다른곳에 존재하면 - 닫는다
        let reply_list_active = document.querySelector('.reply-list-active');
        if (reply_list_active) {
            reply_list_active.classList.remove('reply-list-active');
            reply_list_active.innerHTML = ''
        }

        let postId = event.currentTarget.dataset.postId
        let memberId = event.currentTarget.dataset.memberId

        let temp_html =
            `
            <li>
                <div class="comment-avatar">
                    <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                        alt="">
                </div>
                <div class="comment-box">
                    <div class="comment-head">
                        <h6 class="comment-name"><a href="http://creaticode.com/blog">Lorena Rojero</a></h6>
                    </div>
                    <div style="display: flex; flex-direction: row;">
                        <textarea class="form-control border-3" data-post-id="${postId}" id="comment_textarea" rows="3"
                            placeholder="댓글을 입력해주세요!"></textarea>
                        <button class="btn btn-outline-primary" type="button" id="button-addon3"
                            style="width: 80px;" onclick="write_comment(event)">등록</button>
                    </div>
                </div>
            </li>
            `
        $(`#${reply_list.id}`).append(temp_html)

        $.ajax({
            type: "GET",
            url: `api/comments/${postId}`,
            success: function (comment_dto_list) {

                comment_dto_list.forEach(comment_dto => {
                    let commentId = comment_dto.id
                    let postId = comment_dto.postId
                    let memberId = comment_dto.memberId
                    let comment = comment_dto.comment

                    let temp_comment_html =
                        `
                        <li>
                            <div class="comment-avatar">
                                <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                                    alt="">
                            </div>
                            <div class="comment-box">
                                <div class="comment-head">
                                    <h6 class="comment-name"><a href="http://creaticode.com/blog">Lorena Rojero</a></h6>
                                    <span>hace 10 minutos</span>
                                    <i class="fa-solid fa-trash" data-comment-id="${commentId}"
                                        onclick="delete_comment(event)"></i>
                                </div>
                                <pre
                                    class="comment-content">${comment}</pre>
                            </div>
                        </li>
                        `
                    $(`#${reply_list.id}`).append(temp_comment_html)

                });
            }
        }).fail(function (e) {
            console.log('open_comment fail')
        });

        reply_list.classList.add('reply-list-active')
    }

}

function showSlicedPosts(size, page) {

    if (slice_page <= -1) {
        console.log('마지막 페이지입니다.')
        return
    }

    $.ajax({
        type: "GET",
        url: `/api/slicing-posts?size=${size}&page=${page}`,
        success: function (response) {

            if (response.last == true) {
                slice_page = -99
                is_last = true
            } else {
                slice_page += 1
            }

            let post_list = response.content

            for (let i = 0; i < slice_size; i++) {
                let post = post_list[i]

                let postId = post['id']
                let memberId = post['memberId']
                let content = post['content']
                let commentsCount = post['commentsCount']
                let interested = post['interested']
                let createdAt = post['createdAt']
                let modifiedAt = post['modifiedAt']

                let temp_html =
                    `
                    <li>
                        <div class="comment-main-level">
                            <div class="comment-avatar" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="open_profile(event)">
                                <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="">
                            </div>
                            <div class="comment-box" data-post-id="${postId}" data-member-id="${memberId}" onclick="open_comment(event)">
                                <div class="comment-head">
                                    <h6 class="comment-name by-author">
                                        <a href="#">${memberId}</a>
                                    </h6>
                                    <span>post_id : ${postId}</span>
                                    <span>${createdAt}</span>
                                    <span>${modifiedAt}</span>
                                    <span>${interested}</span>
                                    <span>${commentsCount}</span>
                                    <i class="fa-solid fa-trash" data-post-id="${postId}" onclick="delete_post(event)"></i>
                                    <i class="fa-solid fa-pen" data-post-id="${postId}" onclick="open_modify(event)" data-bs-toggle="modal" data-bs-target="#exampleModal2"></i>
                                    <i class="fa fa-heart" data-post-id="${postId}" onclick="like_post(event)"></i>
                                </div>
                                <pre class="comment-content" id="post_${postId}">${content}</pre>
                            </div>
                        </div>
                        <ul class="comments-list reply-list" id="post_${postId}_comment_list">
                        </ul>
                    </li>
                    `

                $(contentId).append(temp_html)
            }
        }
    }).fail(function (e) {
        console.log('showSlicedPosts fail')
    });
}

function write_post(event) {

    let text = document.querySelector('#textarea').value
    let postData = {
        "memberId": "1", // 임시 하드코딩
        "content": text,
        "interested": "korea" // 임시 하드코딩
    }

    $.ajax({
        type: "post",
        url: `/api/posts/create`,
        data: JSON.stringify(postData),
        contentType: "application/json",
        success: function (response) {
            alert('ok')
            console.log(response)
            location.reload();
        },
        error: function (xhr, status, error) { // 실패 시 실행
            alert("에러 발생: " + error);
        }
    })

}


function open_modify(event) {

    // memberId 검증 과정 거치기

    let postId = event.currentTarget.dataset.postId
    let textarea = document.querySelector("#modify_modal_textarea")

    const comment_box = event.currentTarget.closest(".comment-box");
    const comment_content = comment_box.querySelector(".comment-content");
    const originalText = comment_content.innerText;

    textarea.value = originalText
    textarea.dataset.postId = postId
}

function save_post(event) {
    let textarea = document.querySelector("#modify_modal_textarea")
    let postId = textarea.dataset.postId
    let newText = textarea.value

    let originalText = document.querySelector(`#post_${postId}`)

    let updateData = {
        "memberId": "1", // 임시 하드코딩
        "content": newText,
    }

    $.ajax({
        type: "put",
        url: `/api/posts/modify/${postId}`,
        data: JSON.stringify(updateData),
        contentType: "application/json",
        success: function (response) {
            alert('수정 ok')
            document.querySelector("#modal_close_button").click()
            originalText.innerText = newText;
        },
        error: function (error) { // 실패 시 실행
            alert("에러 발생: " + error);
        }
    })

}

function delete_post(event) {

    let postId = event.currentTarget.dataset.postId
    console.log(postId)

    $.ajax({
        type: "delete",
        url: `/api/posts/delete/${postId}`,
        success: function (response) {
            alert('delete ok')
            location.reload();
        }
    }).fail(function (e) {
        console.log('delete fail')
    });
}


function write_comment(event) {
    let textarea = document.querySelector('#comment_textarea')
    let postId = textarea.dataset.postId
    let newText = textarea.value

    let originalText = document.querySelector(`#post_${postId}`)

    let commentData = {
        "postId": `${postId}`,
        "memberId": "1", // 하드 코딩
        "comment": newText,
    }

    alert('write_comment')

    $.ajax({
        type: "post",
        url: `/api/comments/${postId}/create`,
        data: JSON.stringify(commentData),
        contentType: "application/json",
        success: function (response) {
            alert('댓글작성 ok')
            location.reload();
        },
        error: function (error) { // 실패 시 실행
            alert("에러 발생: " + error);
        }
    })
}

function delete_comment() {
    let commentId = event.currentTarget.dataset.commentId

    $.ajax({
        type: "delete",
        url: `/api/comments/delete/${commentId}`,
        success: function (response) {
            alert('delete ok')
            location.reload();
        }
    }).fail(function (e) {
        console.log('delete fail')
    });
}

function open_profile(event) {
    // alert('open_profile')
}

function like_post(event) {
    alert('like_post')
}

function like_comment() {
    alert('like_comment')
}

function totop() {
    window.scrollTo({ top: 0, left: 0, behavior: 'smooth' })
}

function tobottom() {
    window.scrollTo({ top: document.body.scrollHeight, left: 0, behavior: 'smooth' })
}
