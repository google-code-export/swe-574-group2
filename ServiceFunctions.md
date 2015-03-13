# Service Functions #

This page describes service functions of service module.Function descriptions will be consistent with project requirements.


# Function Details #

1-	login(username,password) //Login işlemi için ekledim

Description: Used when submitting an image to the database

username: username
password: password

Return: ResultId
{
1-	Başarılı
2-	Kullanıcı adı şifre hatalı
3-	Bloklanmış kullanıcı
4-	..
}

[requiresAuthentication](requiresAuthentication.md)
2-	logout() //Web ekranındaki logout işlemi için. Mobilde büyük ihtimalle gerek duyulmaz.
Description: Used for resetting the session

[requiresAuthentication](requiresAuthentication.md)
3-	getHandicapCategories() //kategori dropdownını besleyecek olan data.
Description: Returns array of handicap categories.

Return:

HandicapCategory
{
ID: int
Title: string
}

[requiresAuthentication](requiresAuthentication.md)
4-	getEntryReasons(int handicapCategoryId) //parametre olarak verilen kategori id’nin  reasonlarını dönüyor.
Description: Returns array of entry reasons.

Return:

EntryReason
{
ID: int
Title: string
}

EntryReason
{
ID: int
Name: string
}


5-	[requiresAuthentication](requiresAuthentication.md)
insertEntry(imageMeta,location,category,reason,comment) //Username parametresini kaldırdım. Bu fonksiyonun login fonksiyonu çağırıldıktan sonra çağırılabiliyor olması lazım username zaten seasondan geliyor olmalı güvenlik amacıyla.

Description: Used when submitting an image to the database

imageMeta: unique name, while uploading image can be rename with timestamp\_userid**_prefix //İmajın ismini almamıza bence gerek yok. Sunucuda imaj datası gelince unique isimle kaydetmek lazım. Bence dosya ismi yerine direk imaj datasını verebiliriz. Fotoğrafları android’ten optimize edip göndericem 20 kb civarı olacak dosya boyutu.
location: coordinates
category: id of category
reason: id of reason
comment: optional comment field_

Return:
//Dönüş değerini complex type yapıp geriye işlemde hata oluşursa hatayla ilgili bilgi dönmek lazım.**

InsertEntryResult
{
resultID:
1-	Başarılı
2-	Session düştü
3-	..

entryID: Insert edildiyse entrynin Idsi. Edilemediyse null.
}

6-	getEntryByLocation(location)

Description: Used when getting data from a specific location

Return: JSON object containing  entryID,
username,location,category,reason,comment,imageMeta,imageURL, upCount, downCount

7-	getEntriesByLocations(Array of locations) //Bence 3. Fonksiyon (getEntryByLocation) yeterli. Bu fonksiyonu kullanmamız gerekmez gibi geldi.  Harita tabanlı görüntüleyeceğimiz için noktaları birden fazla noktaya bakmak  mümkün olacak mı?

Description: Used when getting data from multiple locations

Return: JSONArray of JSONObjects entryID , username,location,category,reason,comment,imageMeta,imageURL, upCount, downCount

8-	getEntries() //Web ekranında Google haritasını otomatik olarak kişinin bulunduğu noktaya odaklı başlatırız dolayısıyla web ekranı da bence 3. Fonksiyonu kullanabilir.
Description: Used when getting data from multiple locations for web home page

Return: JSONArray of JSONObjects entryID ,username,location,category,reason,comment,imageMeta,imageURL, upCount, downCount

[requiresAuthentication](requiresAuthentication.md)
9-	updateVote(entryID, vote)
Description: change up or down vote count
entryID: id for the inserted entry
vote: indicated whether is up or down
Return: Result Id //Sonuçla ilgili bir Id dönersek sunucuda oluşan durumu web’de de android’te de gösteririz.
1-	Başarılı
2-	Başarısız, session düştü
3-	..
[requiresAuthentication](requiresAuthentication.md)

[requiresAuthentication](requiresAuthentication.md)
10-	getAbuseReasons() //kötüye kullanım sebepleri, dropdownı beslemek için (rapor etme işleminde)
Description: Returns array of abuse reasons.

Return:

AbuseReason
{
ID: int
Title: string
}

11-	reportAbuse(entryID, reasonId)  //blockEntry sanırım sistemi kötüye kullanımı rapor etmek için olan fonksiyon. İsmini reportAbuse yapabiliriz. Abuse nedeni için de bi Id alırsak daha sonra yazacağımız community logiclerini kolaylaştırır. Mesela aynı sebepten bir entry’de 10+ abuse raporu varsa adamı banla gibi.
Description: Used for reporting that an entry is a possible abuse on system.
entryId: entry Id to report
reportReasonId: report reason Id
Return: ResultId
{
1-	Başarılı
2-	Season düştü
3-	..
}

