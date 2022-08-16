package jr.brian.mybarber.model.data

object Constant {
    const val BASE_URL = "https://passageoftime.me:2333/"

    const val BASE_IMAGE_URL = "https://psmobitech.com/barberapp/"

    const val DEV_PIC_URL = "https://avatars.githubusercontent.com/u/55070185?s=400&u=429b9d32c" +
            "59f72b09275c80be5b7d039ccd5c4ec&v=4"

    const val DEV_WEBSITE_URL = "https://brianjr03.github.io"

    const val GITHUB_PROFILE_URL = "https://github.com/BrianJr03"

    const val LINKEDIN_PROFILE_URL = "https://www.linkedin.com/in/brianjr03/"

    // Men's Cuts

    const val HAIRCUT_PL_1 = "https://preview.redd.it/zvdm1l99tij81.jpg?" +
            "width=640&crop=smart&auto=webp&s=ac3d8f28164" +
            "7085cf246ccc3c2405658b800784a"

    const val HAIRCUT_PL_2 = "https://i.pinimg.com/564x/d3/25/cf/d" +
            "325cf54b05983afa1e0a203471f4d94.jpg"

    const val HAIRCUT_PL_3 = "https://i.pinimg.com/564x/c6/d3/8f/c6" +
            "d38f8835fa4cc46032bbb66c56b5b4.jpg"

    const val HAIRCUT_PL_4 = "https://therighthairstyles.com/wp-content/uploads/900x/designs-for-men/" +
            "1-mens-line-haircut-with-long-top.jpg"

    const val HAIRCUT_PL_5 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9X6yxc" +
            "SkVT5_6Svy3K6mlPjo4786oXJzfYA&usqp=CAU"

    const val HAIRCUT_PL_6 = "https://www.menshairstyletrends.com/wp-content/uploads/2021/01" +
            "/High-fade-haircut-with-beard-nurii_b.jpg"

    const val HAIRCUT_PL_7 = "https://menshaircuts.com/wp-content/uploads/2021/12/how-to-bear" +
            "d-styles-imperial-short-hair-fade-683x1024.jpg"

    // Women's Cuts

    const val W_HAIRCUT_PL_1 = "https://shorthairstyles.com/wp-content/" +
            "uploads/2019/01/19-shaved-tile.jpg"

    const val W_HAIRCUT_PL_2 = "https://i.pinimg.com/originals/dd/48" +
            "/1f/dd481f69eedfc954e7e49d0a4363e025.png"

    const val W_HAIRCUT_PL_3 = "https://static.onecms.io/wp-content/up" +
            "loads/sites/24/2019/11/choppy-lob.jpg"

    const val W_HAIRCUT_PL_4 = "https://images.squarespace-cdn.com/content/v1/55f6d97ae4b" +
            "015f33a4f7103/1537901081624-L8352FPGES24I067GG7Q/Women%27s+haircut.jpg?format=1000w"

    const val W_HAIRCUT_PL_5 = "https://i0.wp.com/www.hadviser.com/wp-content/uploads/2" +
            "020/02/1-trending-hairstyle-for-women-CNfIfNdDDYf.jpg?resize=1012%2C1264&ssl=1"

    const val W_HAIRCUT_PL_6 = "https://i.pinimg.com/736x/83/ad/90/83ad90efb5f27b8802a3c625" +
            "c9e460da.jpg"

    const val W_HAIRCUT_PL_7 = "https://ilesformula.com/wp-content/uploads/2020/02/Screen-S" +
            "hot-2020-02-18-at-12.51.18-PM-e1582026765702.png"

    // Children's Cuts

    const val C_HAIRCUT_PL_1 = "https://lovehairstyles.com/wp-content/uploads/2017/07/b" +
            "oy-haircuts-trends-side-part-683x1024.jpg"

    const val C_HAIRCUT_PL_2 = "https://www.menshairstyletrends.com/wp-" +
            "content/uploads/2020/03/cool-haircuts-for-boys-blackwater_barber-.jpg"

    const val C_HAIRCUT_PL_3 = "https://content.latest-hairstyles.com/wp-content/uploads/comb" +
            "-over-for-boys.jpg"

    const val C_HAIRCUT_PL_4 = "https://menshaircuts.com/wp-content/uploads/2021/01/black-b" +
            "oys-haircuts-buzz-design-short-683x1024.jpg"

    const val C_HAIRCUT_PL_5 = "https://i0.wp.com/therighthairstyles.com/wp-content/upl" +
            "oads/2014/03/9-medium-angled-cut-for-young-girls.jpg?resize=500%2C587&ssl=1"

    const val C_HAIRCUT_PL_6 = "https://hairstyleandmakeup.com/wp-content/uploads/2021/07/" +
            "hairdesignbysonya_149116415_447801793241313_3398229804935714994_n-1024x1024.jpg"

    const val C_HAIRCUT_PL_7 = "https://hairstylecamp.com/wp-content/uploads/bangs-for" +
            "-little-girl.jpg"

    const val SELECTED_BARBER = "SELECTED_BARBER"

    const val SELECTED_SERVICES = "SELECTED_SERVICES"

    const val NOTIFICATIONS = "NOTIFICATIONS"

    const val TIME_SLOTS = "TIME_SLOTS"

    const val APPT_DATE = "APPT_DATE"

    const val F_APPT_DATE = "F_APPT_DATE"

    const val API_TOKEN = "API_TOKEN"

    const val SIGN_IN_RESPONSE = "SIGN_IN_RESPONSE"

    /* Used for testing */
    const val BARBER_SUCCESS_RESPONSE = """
        {
        "status": 0,
        "message": "Successfully",
        "barbers": [
        {
            "barberId": 1,
            "barberName": "Any Barber",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "8888098647",
            "profilePic": "uploads/images/Barbers/1.jpg",
            "gender": "M",
            "breakTimeFrom": "14:00",
            "breakTimeTo": "14:30",
            "hasDefaultServices": 1,
            "holiday": "",
            "userRating": 4.5,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": " ",
            "payment": 0.0
        },
        {
            "barberId": 2,
            "barberName": "Raghvendra Malve",
            "isAdmin": 1,
            "isBarber": 1,
            "mobileNo": "8412901801",
            "profilePic": "uploads/images/Barbers/2.jpg",
            "gender": "M",
            "breakTimeFrom": "13:00",
            "breakTimeTo": "14:00",
            "hasDefaultServices": 1,
            "holiday": "Monday,Monday",
            "userRating": 3.5,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "sharing",
            "payment": 50.0
        },
        {
            "barberId": 3,
            "barberName": "Ranjit Kumar Sen",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "7412901801",
            "profilePic": "/uploads/images/Barbers/_20181201_225829.jpg",
            "gender": "M",
            "breakTimeFrom": "13:00",
            "breakTimeTo": "13:30",
            "hasDefaultServices": 1,
            "holiday": "Wednesday,Wednesday",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "salary",
            "payment": 35000.0
        },
        {
            "barberId": 6,
            "barberName": "Swapnil Raut",
            "isAdmin": 1,
            "isBarber": 1,
            "mobileNo": "9405314565",
            "profilePic": "/uploads/images/Barbers/Rahul_Indian_Santa_Kid_YkdkRGtfdA2.jpg",
            "gender": "F",
            "breakTimeFrom": "13:00",
            "breakTimeTo": "14:00",
            "hasDefaultServices": 1,
            "holiday": "",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "",
            "payment": 0.0
        },
        {
            "barberId": 30,
            "barberName": "Roshan Rote",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "8080909065",
            "profilePic": "/uploads/images/Barbers/Zk1lQ2JZ.jpg",
            "gender": "",
            "breakTimeFrom": "13:00",
            "breakTimeTo": "13:30",
            "hasDefaultServices": 1,
            "holiday": "Monday",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": " ",
            "payment": 0.0
        },
        {
            "barberId": 37,
            "barberName": "krishna\nRaut",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "7083242322",
            "profilePic": "/uploads/images/Barbers/ui-danro.jpg",
            "gender": "",
            "breakTimeFrom": "14:00",
            "breakTimeTo": "14:30",
            "hasDefaultServices": 1,
            "holiday": "Monday,Monday",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "salary",
            "payment": 20000.0
        },
        {
            "barberId": 38,
            "barberName": "Nandkumar Yadav",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "8888444411",
            "profilePic": "/uploads/images/Barbers/IMG-20180928-WA0027.jpg",
            "gender": "",
            "breakTimeFrom": "14:00",
            "breakTimeTo": "14:30",
            "hasDefaultServices": 1,
            "holiday": "Monday",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "sharing",
            "payment": 35.0
        },
        {
            "barberId": 39,
            "barberName": "Suraj Sonawane",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "9775881022",
            "profilePic": "/uploads/images/Barbers/CustomerPhoto_1564396861722.jpg",
            "gender": "",
            "breakTimeFrom": "13:00",
            "breakTimeTo": "13:30",
            "hasDefaultServices": 1,
            "holiday": "Monday,Monday",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "salary",
            "payment": 15000.0
        },
        {
            "barberId": 40,
            "barberName": "Narendra M",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "8885556661",
            "profilePic": "/uploads/images/Barbers/Screenshot_2016-01-03-10-12-57.png",
            "gender": "",
            "breakTimeFrom": "13:00",
            "breakTimeTo": "14:00",
            "hasDefaultServices": 1,
            "holiday": "Friday,Friday",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "salary",
            "payment": 8000.0
        },
        {
            "barberId": 41,
            "barberName": "Prasad\nPawtekar",
            "isAdmin": 0,
            "isBarber": 1,
            "mobileNo": "8010909680",
            "profilePic": "/uploads/images/Barbers/IMG_20211106_023328.jpg",
            "gender": "",
            "breakTimeFrom": "13:00",
            "breakTimeTo": "14:00",
            "hasDefaultServices": 1,
            "holiday": "Friday,Friday",
            "userRating": 0.0,
            "password": "e19d5cd5af0378da05f63f891c7467af",
            "type": "sharing",
            "payment": 50.0
        }
        ]
    }
    """
}