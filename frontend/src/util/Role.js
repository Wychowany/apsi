export const role = {
    ADMINISTRATOR: 'ADMINISTRATOR',

    isAdministrator() {
        return localStorage.getItem('role') === this.ADMINISTRATOR;
    },
};
