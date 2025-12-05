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
const galleryId = '#gallery-image';
const loading_button = document.querySelector('#loading-button')
const loading_button_text = document.querySelector('#loading-button-text')

document.addEventListener("DOMContentLoaded", function () {
    showSlicedImages(slice_size, slice_page)
});

loading_button.addEventListener('click', function () {
    if (is_last) {
        loading_button_text.innerText = '마지막 페이지입니다'
        loading_button.disabled = true
        return
    }
    loading_button.classList.add('loading-button-circle')
    setTimeout(() => {
        showSlicedImages(slice_size, slice_page)
        loading_button.classList.remove('loading-button-circle')
    }, 500);
})


function showSlicedImages(size, page) {

    if (slice_page <= -1) {
        console.log('마지막 페이지입니다.')
        return
    }

    $.ajax({
        type: "GET",
        url: `/api/slicing-images?size=${size}&page=${page}`,
        success: function (response) {

            if (response.last == true) {
                slice_page = -99
                is_last = true
            } else {
                slice_page += 1
            }

            let image_list = response.content

            for (let i = 0; i < size; i++) {
                let image = image_list[i]

                let path = image['path']
                let nickname = image['nickname']
                let comment = image['comment']
                let country = image['country']

                let temp_html =
                    `
                    <div class="img-box">
                        <img src="${path}" alt="" />
                        <div class="transparent-box">
                            <div class="caption">
                                <p>${nickname}</p>
                                <p class="opacity-low">${country}</p>
                            </div>
                        </div>
                    </div>
                    `

                $(galleryId).append(temp_html)
            }
        }
    }).fail(function (e) {
        console.log('showSlicedImages fail')
    });
}

function uploadImage() {
    document.getElementById('image_input').click();
}

document.getElementById("image_input").addEventListener("change", function () {
    const file = this.files[0];
    if (!file) return;

    // 멤버 아이디
    const memberId = 1;

    let formData = new FormData();
    let jsonData = {
        "memberId": memberId,
        "comment": "testtest"
    }

    formData.append("file", file)
    formData.append("imageRequestDto", new Blob([JSON.stringify(jsonData)], { type: "application/json" }))

    $.ajax({
        url: "/api/image",
        type: "POST",
        data: formData,
        // enctype: "multipart/form-data",
        processData: false,  // FormData 전송 시 필수
        contentType: false,  // 헤더 자동 설정
        success: function (response) {
            alert("업로드 성공!");
            console.log(response);
            location.reload();
        },
        error: function (xhr, status, error) {
            alert("에러 발생: " + error);
        }
    });
});

function readURL(input) {
    if (input.files && input.files[0]) {

        var reader = new FileReader();

        reader.onload = function (e) {
            $('.image-upload-wrap').hide();

            $('.file-upload-image').attr('src', e.target.result);
            $('.file-upload-content').show();

            // $('.image-title').html(input.files[0].name);
        };

        reader.readAsDataURL(input.files[0]);

    } else {
        removeUpload();
    }
}

function removeUpload() {
    $('.file-upload-input').replaceWith($('.file-upload-input').clone());
    $('.file-upload-content').hide();
    $('.image-upload-wrap').show();
}
$('.image-upload-wrap').bind('dragover', function () {
    $('.image-upload-wrap').addClass('image-dropping');
});
$('.image-upload-wrap').bind('dragleave', function () {
    $('.image-upload-wrap').removeClass('image-dropping');
});
