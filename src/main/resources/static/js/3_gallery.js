// https://hayden-igm.tistory.com/39

function totop() {
    window.scrollTo({ top: 0, left: 0, behavior: 'smooth' })
}

function tobottom() {
    window.scrollTo({ top: document.body.scrollHeight, left: 0, behavior: 'smooth' })
}

const slice_size = 10
let slice_page = 0
let is_last = false
const contentId = '#comments-list';
const loading_button = document.querySelector('#loading-button')
const loading_button_text = document.querySelector('#loading-button-text')

loading_button.addEventListener('click', function () {
    if (is_last) {
        loading_button_text.innerText = '마지막 페이지입니다'
        loading_button.disabled = true
        return
    }
    loading_button.classList.add('loading-button-circle')
    setTimeout(() => {
        // showSlicedPosts(slice_size, slice_page)
        // showImages()
        loading_button.classList.remove('loading-button-circle')
    }, 500);
})


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