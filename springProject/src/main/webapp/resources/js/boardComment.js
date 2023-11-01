console.log(bnoVal+"bnoVal");

async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
    const config ={
        method: "post",
        headers: {
            'content-type':'application/json; charset=utf-8'
        },
        body: JSON.stringify(cmtData)
    };
    const resp = await fetch(url,config);
    const result = await resp.text();
    return result;
    } catch (error) {
        console.log(error);
    } 
}

document.getElementById('cmtPostBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText').value;
    const cmtWriter = document.getElementById('cmtWriter').innerText;
    console.log(cmtText+"<< cmtText   "+cmtWriter+"<< cmtWriter")
    if(cmtText =="" || cmtText==null){
        alert("댓글을 입력해주세요");
        document.getElementById("cmtText").focus;
        return false;
    }else{
        let cmtData ={
            bno: bnoVal,
            writer: cmtWriter,
            content: cmtText
        }
        console.log(cmtData);
        postCommentToServer(cmtData).then(result=>{
            console.log(result + "<< result");
            if(result == 1){
                alert("댓글 등록 성공!");
                document.getElementById('cmtText').value = "";
                getCommentList(bnoVal);
            }

        })
    }
})

async function spreadCommentListFromServer(bno, page){
    try {
        const resp = await fetch("/comment/"+bno+"/"+page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// 무조건 처음 뿌릴땐 첫 페이지 값을 뿌려야 함.
function getCommentList(bno, page=1){
    spreadCommentListFromServer(bno, page).then(result=>{
        console.log(result); // ph 객체 (pgvo, totalCount, cmtList)
        let div = document.getElementById('cmtArea');
        if(result.cmtList.length > 0){
            // 1page 일 경우만 
            if(page == 1){
                div.innerHTML = "";
            }
            for(let i=0; i<result.cmtList.length; i++){
                let str = `<tr data-cno="${result.cmtList[i].cno}" data-content="${result.cmtList[i].content}">`;
                str += `<td>${result.cmtList[i].cno}</td>`
                str += `<td>${result.cmtList[i].writer}</td>`;
                str += `<td>${result.cmtList[i].content}</td>`;
                str += `<td>${result.cmtList[i].modAt}</td>`;
                str += `<td> <button type="button" class="btn btn-danger delBtn">삭제</button> </td>`;
                str += `<td> <button type="button" class="btn btn-success modBtn" data-bs-toggle="modal" data-bs-target="#myModal">수정</button> </td></tr>`;
                // if(result.cmtList[i].writer){

                // }
                div.innerHTML += str;
            }
            // 댓글 페이징 코드
            let moreBtn = document.getElementById('moreBtn');
            // DB에서 pgvo + list 같이 가져와야 값을 줄 수 있음.
            if(result.pgvo.pageNo < result.endPage || result.next){
                moreBtn.style.visibility = 'visible'; // 버튼 표시
                moreBtn.dataset.page = page + 1;
            }else{
                moreBtn.style.visibility = 'hidden'; // 버튼 숨김
            }
        }
    })
}

async function removeCommentToServer(cno){
    try {
        const url = "/comment/"+cno;
        const config ={
            method: "delete"
        };
        const resp = await fetch(url,config);
        const result =await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('delBtn')){
        let tr = e.target.closest("tr");
        let cnoVal = tr.dataset.cno;
        
        removeCommentToServer(cnoVal).then(result=>{
            if(result == 1){
                alert("댓글삭제 성공!");
                getCommentList(bnoVal);
            }else{
                console.log("댓글삭제 실패");
            }
        })
        
    }else if(e.target.classList.contains('modBtn')){
        let tr = e.target.closest("tr");
        let cnoVal = tr.dataset.cno;
        console.log("cnoVal : "+ cnoVal);
        // nextSibling : 같은 부모의 다음 형제 객체를 반환
        let cmtText = tr.dataset.content;
        console.log("cmtText : " + cmtText);
        //기존내용 모달창에 반영
        document.getElementById('cmtTextMod').value = cmtText;
        // cmtModBtn 에 data-cno 달기
        document.getElementById("cmtModBtn").setAttribute('data-cno',tr.dataset.cno);
    }else if(e.target.id == "cmtModBtn"){
        let cmtDataMod={
            cno: e.target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        };
        console.log(cmtDataMod);
        editCommentToServer(cmtDataMod).then(result=>{
            if(parseInt(result)){
                document.querySelector('.btn-close').click();
            }
            getCommentList(bnoVal);
    })
   
    
    
} else if(e.target.id == 'moreBtn'){
    getCommentList(bnoVal, parseInt(e.target.dataset.page));
}
})


async function editCommentToServer(cmtDataMod){
    try {
        const url = "/comment/"+cmtDataMod.cno;
        const config = {
            method: 'put',
            headers: {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtDataMod)
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

