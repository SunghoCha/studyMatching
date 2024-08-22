export default {

    getNumberFormatted(val) {

        // val이 숫자일 때만 포맷팅
        if (typeof val === 'number' && !isNaN(val)) {
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        } else {
            console.warn('Invalid value for formatting:', val);
            return 'Invalid';  // 숫자가 아닌 경우 처리
        }
    }
}