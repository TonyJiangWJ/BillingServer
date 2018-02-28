/**
 * rsa加密
 * @param str 待加密文本
 * @param pubKey rsa公钥
 * @returns {string|*|PromiseLike<ArrayBuffer>}
 */
function getRsaCipher(str, pubKey) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(pubKey);
    var timestamp = new Date().getTime();
    return encrypt.encrypt(timestamp + str);
}

/**
 * 判断字符串是否为非空
 * @param str
 * @returns {boolean}
 */
function isNotEmpty(str) {
    return str !== null && typeof str !== 'undefined' && str !== "";
}

/**
 * 判断字符串是否为空
 * @param str
 * @returns {boolean}
 */
function isEmpty(str) {
    return !isNotEmpty(str);
}