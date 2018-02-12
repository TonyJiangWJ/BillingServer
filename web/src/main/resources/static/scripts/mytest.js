/**
 * Created by TonyJiang on 2017/5/12.
 */

function convertUtf8(encodeStr){
    var regxe = /\\u(\d{4})/;
    // regxe.match(encodeStr)
    if(regxe.test(encodeStr)){
        console.log("yes");
    }else{

    }
}
