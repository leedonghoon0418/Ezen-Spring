console.log("board Modify in!!");

async function removeFileToServer(uuid){
    try {
        const url = "/board/file/"+uuid;
        const config = {
            method: "delete"
        }

        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;

    } catch (error) {
        console.log(error);
    }
}

document.addEventListener('click',(e)=>{
    if(e.target.id == "fileRemoveBtn"){
        let li = e.target.closest('li');
        let uuid = li.dataset.uuid;
        console.log(uuid);
        removeFileToServer(uuid).then(result=>{
            if(result=="1"){
                alert("사진 삭제 성공");
                e.target.closest('li').remove;
                location.reload();
            }else{
                alert("사진 삭제 실패");
            }
        })
    }

    
})