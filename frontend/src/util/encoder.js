export const encoder = {
    encodeEmail(email) {
        return email.replace('+', '%2B');
    }
};