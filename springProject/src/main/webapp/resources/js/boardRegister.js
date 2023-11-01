console.log("boardRegister.js In~~");
//트리거 버튼 처리 -> trigger 누르면 -> id가 files 인거 실행
document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('files').click();
});

// 실행파일, 이미지 파일에 대한 정규 표현식 작성(\ 시작, $ 끝)
const regExp = new RegExp("\.(exe|sh|bat|js|msi|dll)$"); // 실행파일 막기
const regExpImg = new RegExp("\.(jpg|jpeg|png|gif)$"); // 허용하는 이미지 파일 목록
const maxSize = 1024*1024*20; // 20MB (파일 최대사이즈)

function fileValidation(fileName, fileSize){
    if(!regExpImg.test(fileName)){
        // 이미지X 0
        return 0;
    }else if(regExp.test(fileName)){
        //실행파일 0
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else{
        return 1;
    }
}

document.addEventListener('change',(e)=>{
    if(e.target.id == 'files'){
        // 파일을 다시 추가할 때는 버튼 상태를 원래대로 변경해줘야함.
        document.getElementById('regBtn').disabled = false; 

        // input file element에 저장된 해당 파일의 정보를 가져오는 property 
        const fileObject = document.getElementById('files').files;

        console.log(fileObject);

        //첨부파일에 대한 기본 정보를 fileZone에 기록
        let div = document.getElementById('fileZone');

        //기존값이 있으면 삭제
        div.innerHTML="";
        // ul => li로 처리  

        // 여러파일이 파일인지 검증에 통과해야 하기 때문에 *로 각 파일마가 통과여부 확인
        let isOk = 1;
        let ul = `<ul class="list-group list-group-flush">`;
        for(let file of fileObject){
            let vaildResult = fileValidation(file.name,file.size); // 0 or 1 로 리턴 
            isOk *= vaildResult;
            ul += `<li class="list-group-item">`;
            ul += `<div class="mb-3">`;
            ul += `${vaildResult ? '<div class="mb-3" >업로드 가능</div>' : '<div class="mb-3 text-danger">업로드 불가능</div>'}`;
            ul += `${file.name}</div>`;
            ul += `<span class="badge text-bg-${vaildResult ? "success":"danger"}">${file.size}Bytes</span></li>`;
        }
        ul += `</ul>`
        div.innerHTML += ul;
        if(isOk == 0){
            //버튼 비활성화
            document.getElementById('regBtn').disabled = true;
        }
    }
});