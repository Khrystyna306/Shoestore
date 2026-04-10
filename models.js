var Model = {
    currentUser: JSON.parse(localStorage.getItem('user')) || { role: 'guest' },

    users: JSON.parse(localStorage.getItem('users')) || [
        { id: 1, name: 'Admin', role: 'admin' },
        { id: 2, name: 'User', role: 'user' }
    ],

    saveUsers: function() { localStorage.setItem('users', JSON.stringify(this.users)); },
    login: function(username, password) {
        if (username==='admin' && password==='admin') this.currentUser={role:'admin'};
        else if (username==='user' && password==='user') this.currentUser={role:'user'};
        else return false;
        localStorage.setItem('user', JSON.stringify(this.currentUser));
        return true;
    },
    logout: function() {
        this.currentUser={role:'guest'};
        localStorage.setItem('user', JSON.stringify(this.currentUser));
    },
    addUser: function(name, role) {
        var id = Date.now();
        this.users.push({id:id, name:name, role:role});
        this.saveUsers();
    },
    deleteUser: function(id) {
        this.users = this.users.filter(u => u.id !== id);
        this.saveUsers();
    }
};
