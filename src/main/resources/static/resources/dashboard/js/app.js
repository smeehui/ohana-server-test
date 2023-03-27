class App {
    static DOMAIN=location.origin;
    static BASE_URL_POST=this.DOMAIN +"/api/post";
    static BASE_URL_USER=this.DOMAIN +"/api/user";
    static BASE_URL_UNAPPROVED=this.DOMAIN +"/api/post";

}

class User {
    constructor(id, fullName, email, phone, address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
class PostMedia {
    constructor(id, fileUrl,) {
        this.id = id;
        this.fileUrl = fileUrl;
    }
}




class Utility{
    constructor(id, name,icon,priority) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.priority = priority;
    }
}


// class Post {
//     constructor(id, rentHouse, title, slug, descriptionContent, createdAt, location, user, thumbnailUrl, images, categoryId, utilities, status) {
//          this.id = id;
//         this.rentHouse = rentHouse;
//         this.title = title;
//         this.slug = slug;
//         this.descriptionContent = descriptionContent;
//         this.createdAt = createdAt;
//         this.location = location;
//         this.user = user;
//         this.thumbnailUrl = thumbnailUrl;
//         this.images = images;
//         this.categoryId = categoryId;
//         this.utilities = utilities;
//         this.status = status;
//     }
// }

function  showSuccess(msg) {
    Swal.fire({
        position: 'top-end',
        title: msg,
        icon: 'success',
        showConfirmButton: false,
        timer: 1500,
    });
}
function  showErrors(msg) {
    Swal.fire({
        position: 'mid',
        title: msg,
        icon: 'error',
        showConfirmButton: false,
        timer: 1500,
    });
}