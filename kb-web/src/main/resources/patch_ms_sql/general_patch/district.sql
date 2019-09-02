
BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from district)

if(@count < 75)
BEGIN
SET IDENTITY_INSERT district ON
INSERT  INTO district (id, province_id, name) VALUES
(1, 1, 'Taplejung'),
(2, 1, 'Panchthar'),
(3, 1, 'Ilam'),
(4, 1, 'Jhapa'),
(5, 1, 'Morang'),
(6, 1, 'Sunasari'),
(7, 1, 'Dhankuta'),
(8, 1, 'Terhathum'),
(9, 1, 'Sankhusabha'),
(10, 1, 'Bhojpur'),
(11, 1, 'Solukhumbu'),
(12, 1, 'Okhaldunga'),
(13, 1, 'Khotang'),
(14, 1, 'Udayapur'),
(15, 2, 'Saptari'),
(16, 2, 'Siraha'),
(17, 2, 'Dhanusha'),
(18, 2, 'Mahottari'),
(19, 2, 'Sarlahi'),
(20, 2, 'Rautahat'),
(21, 2, 'Bara'),
(22, 2, 'Parsa'),
(23, 3, 'Sindhuli'),
(24, 3, 'Ramechhap'),
(25, 3, 'Dolakha'),
(26, 3, 'Sindhupalchowk'),
(27, 3, 'Kavrepalanchok'),
(28, 3, 'Lalitpur'),
(29, 3, 'Bhaktapur'),
(30, 3, 'Kathmandu'),
(31, 3, 'Nuwakot'),
(32, 3, 'Rasuwa'),
(33, 3, 'Dhading'),
(34, 3, 'Makawanpur'),
(35, 3, 'Chitwan'),
(36, 4, 'Gorkha'),
(37, 4, 'Lamjung'),
(38, 4, 'Tanahun'),
(39, 4, 'Syangja'),
(40, 4, 'Kaski'),
(41, 4, 'Manang'),
(42, 4, 'Mustang'),
(43, 4, 'Myagdi'),
(44, 4, 'Parbat'),
(45, 4, 'Baglung'),
(46, 4, 'Nawalparasi'),
(47, 5, 'Gulmi'),
(48, 5, 'Palpa'),
(49, 5, 'Rupandehi'),
(50, 5, 'Kapilbastu'),
(51, 5, 'Arghakhanchi'),
(52, 5, 'Pyuthan'),
(53, 5, 'Rolpa'),
(54, 5, 'Rukum'),
(55, 5, 'Dang'),
(56, 5, 'Banke'),
(57, 5, 'Bardiya'),
(58, 6, 'Salyan'),
(59, 6, 'Surkhet'),
(60, 6, 'Dailekh'),
(61, 6, 'Jajarkot'),
(62, 6, 'Dolpa'),
(63, 6, 'Jumla'),
(64, 6, 'Kalikot'),
(65, 6, 'Mugu'),
(66, 6, 'Humla'),
(67, 7, 'Bajura'),
(68, 7, 'Bajhang'),
(69, 7, 'Achham'),
(70, 7, 'Doti'),
(71, 7, 'Kailali'),
(72, 7, 'Kanchanpur'),
(73, 7, 'Dadeldhura'),
(74, 7, 'Baitadi'),
(75, 7, 'Darchula')

SET IDENTITY_INSERT district Off
END
else
BEGIN
SET IDENTITY_INSERT district Off
END
END ;
