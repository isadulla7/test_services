package uz.fido.spring_kotlin_firebase.entity

data class DynamicLinkInfo (
       val domainUriPrefix: String,
       val link: String,
       val androidInfo: AndroidInfo,
       val iosInfo: IosInfo
)