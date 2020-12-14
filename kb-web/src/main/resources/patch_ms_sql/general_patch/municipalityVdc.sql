
BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from municipality_vdc)

if(@count < 764)
BEGIN

SET IDENTITY_INSERT municipality_vdc ON
INSERT INTO municipality_vdc (id, district_id, name) VALUES
(1, 1, 'Phungling'),
(2, 1, 'Aatharai Triveni'),
(3, 1, 'Sidingawa'),
(4, 1, 'Phakatanglung'),
(5, 1, 'Mikwa Khola'),
(6, 1, 'Meringden'),
(7, 1, 'Maiwa Khola'),
(8, 1, 'Yangbarak'),
(9, 1, 'Sirijunga'),
(10, 2, 'Phidim'),
(11, 2, 'Phalelung'),
(12, 2, 'Palgunanda'),
(13, 2, 'Hilihang'),
(14, 2, 'Kummayak'),
(15, 2, 'Miklajung'),
(16, 2, 'Tumbewa'),
(17, 2, 'Yangbarak'),
(18, 3, 'Ilam'),
(19, 3, 'Deumai'),
(20, 3, 'Mai'),
(21, 3, 'Suryodaya'),
(22, 3, 'Phakaphokthum'),
(23, 3, 'Chulachuli'),
(24, 3, 'Mai Jogmai'),
(25, 3, 'Mangsebung'),
(26, 3, 'Rong'),
(27, 3, 'Sandakpur'),
(28, 4, 'Mechi Nagar '),
(29, 4, 'Damak'),
(30, 4, 'Kankai'),
(31, 4, 'Bhadrapur'),
(32, 4, 'Arjundhara'),
(33, 4, 'Shiva Sataxi'),
(34, 4, 'Gauradaha'),
(35, 4, 'Birtamod'),
(36, 4, 'Kamal'),
(37, 4, 'Gaurigunj'),
(38, 4, 'Barhadashi'),
(39, 4, 'Jhapa'),
(40, 4, 'BuddaShanti'),
(41, 4, 'Haldibari'),
(42, 4, 'Kanchankawal'),
(43, 5, 'Biratnagar Sub-Metrpolitan'),
(44, 5, 'Belbari'),
(45, 5, 'Letang'),
(46, 5, 'Pathari-Sanishchare'),
(47, 5, 'Rangeli'),
(48, 5, 'Ratuwamai'),
(49, 5, 'Sunbarshi'),
(50, 5, 'Urlabari'),
(51, 5, 'SundarHaraincha'),
(52, 5, 'Budhi Ganga'),
(53, 5, 'Dhanpalthan'),
(54, 5, 'Gramthan'),
(55, 5, 'Jahada'),
(56, 5, 'Kanepokhari'),
(57, 5, 'Katahari'),
(58, 5, 'Kerabari'),
(59, 5, 'Miklajung'),
(60, 6, 'Itahari Sub-Metropolitan'),
(61, 6, 'Dharan Sub-Metropolitan'),
(62, 6, 'Inaruwa'),
(63, 6, 'Duhabi'),
(64, 6, 'Ramdhuni'),
(65, 6, 'Baraha'),
(66, 6, 'Dewangunj'),
(67, 6, 'Koshi'),
(68, 6, 'Gadhi'),
(69, 6, 'Barju'),
(70, 6, 'Bhokraha'),
(71, 6, 'Harinagara'),
(72, 7, 'Pakhribas'),
(73, 7, 'Dhankuta'),
(74, 7, 'Mahalaxmi'),
(75, 7, 'SanguriGadhi'),
(79, 7, 'Khalsa Chhintang Sahidbhumi'),
(80, 7, 'Chhathat Jorpati'),
(81, 7, 'Chaubise'),
(82, 8, 'Myanglung'),
(83, 8, 'Laligurans'),
(84, 8, 'Aatharai'),
(85, 8, 'Chhathar'),
(86, 8, 'Phedap'),
(87, 8, 'Menchayayem'),
(88, 9, 'Chainpur'),
(89, 9, 'DharmaDevi'),
(90, 9, 'Khadbari'),
(91, 9, 'Madi'),
(92, 9, 'PanchKhapan'),
(93, 9, 'Bhot Khola'),
(94, 9, 'Chichila'),
(95, 9, 'Makalu'),
(96, 9, 'Sabhapokhari'),
(97, 9, 'Silingchong'),
(98, 10, 'Bhojpur'),
(99, 10, 'Shadananda'),
(100, 10, 'Tyamke Maiyum'),
(101, 10, 'Ram Prasad Rai'),
(102, 10, 'Arun'),
(103, 10, 'PauwaDungama'),
(104, 10, 'Salpasilichho'),
(105, 10, 'Aamchok'),
(106, 10, 'HatuwaGadhi'),
(107, 11, 'SoluDudhakund'),
(108, 11, 'Dudhakoshi'),
(109, 11, 'Khumbu PasangLhamu'),
(110, 11, 'Dudhakaushika'),
(111, 11, 'Necha Salyan'),
(112, 11, 'Maha Kulung'),
(113, 11, 'Likhu Pike'),
(114, 11, 'Sotang'),
(115, 12, 'SiddiCharan'),
(116, 12, 'Khiji Demba'),
(117, 12, 'Champadevi'),
(118, 12, 'ChishankhuGadhi'),
(119, 12, 'ManeBhanjyang'),
(120, 12, 'Molung'),
(121, 12, 'Likhu'),
(122, 12, 'Sunkoshi'),
(123, 13, 'Halesi Tuwachung'),
(124, 13, 'Rupakot Majhuwagadhi'),
(125, 13, 'Aiselukharka'),
(126, 13, 'Lamidanda'),
(127, 13, 'Jantedhunga '),
(128, 13, 'Khotehang'),
(129, 13, 'Kepilasgadhi'),
(130, 13, 'Diprung'),
(131, 13, 'Sakela'),
(132, 13, 'Barahpokhari'),
(133, 14, 'Katari'),
(134, 14, 'ChaudandiGadhi'),
(135, 14, 'Triyuga'),
(136, 14, 'Belaka'),
(137, 14, 'Udayapur Gadhi'),
(138, 14, 'Tapli'),
(139, 14, 'Rautamai'),
(140, 14, 'Sunkoshi'),
(141, 15, 'Rajbiraj'),
(142, 15, 'Kanchanrup'),
(143, 15, 'Dakneshwori'),
(144, 15, 'BodeBarsain'),
(145, 15, 'Khadak'),
(146, 15, 'Shambhunath'),
(147, 15, 'Surunga'),
(148, 15, 'HanumanNagar Kankalini'),
(149, 15, 'Krishna Sawaran'),
(150, 15, 'Chhinnamasta'),
(151, 15, 'Mahadeva'),
(152, 15, 'Saptkoshi'),
(153, 15, 'Tirhut'),
(154, 15, 'Tilathi Koiladi'),
(155, 15, 'Rupani'),
(156, 15, 'Belhi Chapani'),
(157, 15, 'Bishnupura'),
(158, 16, 'Lahan'),
(159, 16, 'DhanagadhiMai'),
(160, 16, 'Siraha'),
(161, 16, 'GolBazaar'),
(162, 16, 'Mirchaiya'),
(163, 16, 'Kalyanpur'),
(164, 16, 'Bhagawanpur'),
(165, 16, 'Aaurahi'),
(166, 16, 'Bishnupur'),
(167, 16, 'Sukhipur'),
(168, 16, 'Karjanha'),
(169, 16, 'Bariyarpatti'),
(170, 16, 'Laxmipur Patari'),
(171, 16, 'Naraha'),
(172, 16, 'Sakhuwanankarkatt'),
(173, 16, 'Arnama'),
(174, 16, 'Nawarajpur'),
(175, 17, 'Janakpur Sub-Metropolitan'),
(176, 17, 'Chhireshwarnath'),
(177, 17, 'Ganeshman Charnath'),
(178, 17, 'Dhanusadham'),
(179, 17, 'Nagaraen'),
(180, 17, 'Bideh'),
(181, 17, 'Mithila'),
(182, 17, 'Shahid Nagar'),
(183, 17, 'Sabaila'),
(184, 17, 'Kamala Siddidatri'),
(185, 17, 'Janak Nandini'),
(186, 17, 'Bateshwor'),
(187, 17, 'Mithila Bihari'),
(188, 17, 'Mukhiyapatti Musaharmiya'),
(189, 17, 'Laxminiya'),
(190, 17, 'Hanspur'),
(191, 17, 'Aaurahi'),
(192, 18, 'Jaleshwor'),
(193, 18, 'Bardibas'),
(194, 18, 'Gaushala'),
(195, 18, 'Ekadara'),
(196, 18, 'Sonama'),
(197, 18, 'Samsi'),
(198, 18, 'Loharpatti'),
(199, 18, 'RamGopalpur'),
(200, 18, 'Mahottari'),
(201, 18, 'Manara'),
(202, 18, 'Matihani'),
(203, 18, 'Bhangaha'),
(204, 18, 'Balawa'),
(205, 18, 'Pipara'),
(206, 18, 'Aaurahi'),
(207, 19, 'Ishworpur'),
(213, 19, 'Malangawa'),
(214, 19, 'Lalbandi'),
(215, 19, 'Haripur'),
(216, 19, 'Haripurwa'),
(217, 19, 'Hariwan'),
(218, 19, 'Barahathawa'),
(219, 19, 'Balara'),
(220, 19, 'Godaita'),
(221, 19, 'Bagamati'),
(222, 19, 'Kabilasi'),
(223, 19, 'Chakraghatta'),
(224, 19, 'Chandra Nagar'),
(225, 19, 'DhanaKoul'),
(226, 19, 'Bramhapuri'),
(227, 19, 'Ram Nagar'),
(228, 19, 'Bishnu'),
(229, 20, 'Chandrapur'),
(230, 20, 'Garuda'),
(231, 20, 'Gaur'),
(232, 20, 'BoudhiMai'),
(233, 20, 'Brindaban'),
(234, 20, 'Devahi Gonahi'),
(235, 20, 'Durga Bhagawati'),
(236, 20, 'GadhiMai'),
(237, 20, 'Gujara'),
(238, 20, 'Katahariya'),
(239, 20, 'Madhav Narayan'),
(240, 20, 'Moulapur'),
(241, 20, 'Phatuwa Bijayapur'),
(242, 20, 'IshNath'),
(243, 20, 'Paroha'),
(244, 20, 'Rajpur'),
(245, 21, 'Kalaiya Sub-Metrpolish'),
(246, 21, 'Jitpur Simara Sub-Metrpolish'),
(247, 21, 'Kolhabi'),
(248, 21, 'Nijgadh'),
(249, 21, 'Maha Gahdimai'),
(250, 21, 'Simraun Gadh'),
(251, 21, 'Adarsha Kotwal'),
(252, 21, 'Karaiya Mai'),
(253, 21, 'Devtaal'),
(254, 21, 'PachaRouta'),
(255, 21, 'Parawanipur'),
(256, 21, 'Prasouni'),
(257, 21, 'Pheta'),
(258, 21, 'Bara Gadhi'),
(259, 21, 'Subarna'),
(260, 22, 'Birgunj Sub-Metropolitan'),
(261, 22, 'Pokhariya'),
(262, 22, 'Subarnapur'),
(263, 22, 'Jagarnathapur'),
(264, 22, 'Dhobini'),
(265, 22, 'Chhipahar Mai'),
(266, 22, 'Pakaha Mainpur'),
(267, 22, 'Bindabasini'),
(268, 22, 'Bahudar Mai'),
(269, 22, 'Belawa'),
(270, 22, 'Parsa Gadhi'),
(271, 22, 'Sakhuwa Prasouni'),
(272, 22, 'Paterwa Sugauli'),
(273, 23, 'Kamalamai'),
(274, 23, 'Dudhauli'),
(275, 23, 'Golanjor'),
(276, 23, 'Ghyanglekh'),
(277, 23, 'Teen Patan'),
(278, 23, 'Phikkal'),
(279, 23, 'Marin'),
(280, 23, 'Sunkoshi'),
(281, 23, 'Hariharpur Gadhi'),
(282, 24, 'Manthali'),
(283, 24, 'Ramechhap'),
(284, 24, 'Umakunda'),
(285, 24, 'KhandaDevi'),
(286, 24, 'Gokul Ganga'),
(287, 24, 'Doramba'),
(288, 24, 'Likhu'),
(289, 24, 'Sunapati'),
(290, 25, 'Jiri'),
(291, 25, 'Bhimeshwor'),
(292, 25, 'Kalinchok'),
(293, 25, 'Gauri Shankar'),
(294, 25, 'Tamakoshi'),
(295, 25, 'Melung'),
(296, 25, 'Bigu'),
(298, 25, 'Baiteshwor'),
(299, 25, 'Shailung'),
(300, 26, 'Chautara Sangachokgadhi '),
(301, 26, 'Barhabise'),
(302, 26, 'Melamchi'),
(303, 26, 'Indrawati'),
(304, 26, 'Jugal'),
(305, 26, 'PanchaPokhari'),
(306, 26, 'Balephi'),
(307, 26, 'Bhotekoshi'),
(308, 26, 'Lishankhu Pakhar'),
(309, 26, 'Sunkoshi'),
(310, 26, 'Helambu'),
(311, 26, 'TripuraSundari'),
(312, 27, 'Dhulikhel'),
(313, 27, 'Banepa'),
(314, 27, 'Panauti'),
(315, 27, 'Panchkhal'),
(316, 27, 'Namobuddha'),
(317, 27, 'Mandan Deupur'),
(318, 27, 'Khanikhola'),
(319, 27, 'Chaunri Deurali'),
(320, 27, 'Temal'),
(321, 27, 'Bethanchok'),
(322, 27, 'Bhumlu'),
(323, 27, 'Mahabharat'),
(324, 27, 'Roshi'),
(325, 28, 'Lalitpur Metropolitan'),
(326, 28, 'Godawari'),
(327, 28, 'MahaLaxmi'),
(328, 28, 'Konjyosom'),
(329, 28, 'Bagmati'),
(330, 28, 'Mahankal'),
(331, 29, 'Changunarayan'),
(332, 29, 'Bhaktapur'),
(333, 29, 'Madhyapur'),
(334, 29, 'Surya Binayak'),
(335, 30, 'Kathmandu Metropolitan'),
(336, 30, 'Kageswori-Manohara'),
(337, 30, 'Kirtipur'),
(338, 30, 'Gokarneshwor'),
(339, 30, 'Chandragiri'),
(340, 30, 'Tokha'),
(341, 30, 'Tarkeshwor'),
(342, 30, 'Daxinkali'),
(343, 30, 'Nagarjun'),
(344, 30, 'Budhanialkantha'),
(345, 30, 'Sankharapur'),
(346, 31, 'Bidur'),
(347, 31, 'Belkot Gadhi'),
(348, 31, 'Kakani'),
(349, 31, 'Kispang'),
(350, 31, 'Tadi'),
(351, 31, 'Tarkeshwor'),
(352, 31, 'Dupcheshwor'),
(353, 31, 'PanchaKanya'),
(354, 31, 'Likhu'),
(355, 31, 'Meghang'),
(356, 31, 'Shivpuri'),
(357, 31, 'Surya Gadhi'),
(358, 32, 'Uttar Gaya'),
(359, 32, 'Kalika'),
(360, 32, 'GosaiKunda'),
(361, 32, 'NauKunda'),
(362, 32, 'ParbatiKunda'),
(363, 33, 'Dhunibesi'),
(364, 33, 'Nilkantha'),
(365, 33, 'Khaniyabas'),
(366, 33, 'Gajuri'),
(367, 33, 'Galchhi'),
(368, 33, 'Ganga Jamuna'),
(369, 33, 'Jwalamukhi'),
(370, 33, 'Thakre'),
(371, 33, 'Netrawati'),
(372, 33, 'Benighat Rorang'),
(373, 33, 'Rubi Valley'),
(374, 33, 'Sidda Lekh'),
(375, 33, 'Tripura Sundari'),
(376, 34, 'Hetaunda Sub-Metropolitan'),
(377, 34, 'Thaha '),
(378, 34, 'Indra Sarobar'),
(379, 34, 'Kailash'),
(380, 34, 'Bakaiya'),
(381, 34, 'Bagmati'),
(382, 34, 'Bhimphedi'),
(383, 34, 'Makawanpur Gadhi'),
(384, 34, 'Manahari'),
(385, 34, 'Raksirang'),
(388, 35, 'Bharatpur Metropolitan'),
(389, 35, 'Kalika'),
(390, 35, 'Khairhani'),
(391, 35, 'Madi'),
(392, 35, 'Ratna Nagar'),
(393, 35, 'Rapti'),
(394, 35, 'Ichchhakamana'),
(395, 36, 'Gorkha'),
(396, 36, 'Palungtar'),
(397, 36, 'Sulikot'),
(398, 36, 'Siranchok'),
(399, 36, 'Ajirkot'),
(400, 36, 'Aarughat'),
(401, 36, 'Gandaki'),
(402, 36, 'Chumanubri'),
(403, 36, 'Dharche'),
(404, 36, 'Bhimsen'),
(405, 36, 'Shahid Lakhan'),
(406, 37, 'Besishahar'),
(407, 37, 'Madhya Nepal'),
(408, 37, 'Rainas'),
(409, 37, 'Sundarbazar'),
(410, 37, 'Kabahola Sothar'),
(411, 37, 'Dhudh Pokhari'),
(412, 37, 'Dordi'),
(413, 37, 'Marsyangdi'),
(414, 38, 'Bhanu'),
(415, 38, 'Bhimad Minucipality'),
(416, 38, 'Byas Minucipality'),
(417, 38, 'Sukla Gandaki'),
(418, 38, 'Aanbu Khaireni'),
(419, 38, 'Rishing'),
(420, 38, 'Ghiring'),
(421, 38, 'Devghat'),
(422, 38, 'Myagde'),
(423, 38, 'Bandipur'),
(424, 39, 'Galyang'),
(425, 39, 'Chapakot'),
(426, 39, 'Putalibazar'),
(427, 39, 'Bhirkot'),
(428, 39, 'Waling'),
(429, 39, 'Arjun Chaupari'),
(430, 39, 'Aandhi Khola'),
(431, 39, 'Kali Gandaki'),
(432, 39, 'Phedi Khola'),
(433, 39, 'Biruwa'),
(434, 39, 'Harinas'),
(435, 40, 'Pokhara Lekhnath Metropolitan'),
(436, 40, 'Annapurna'),
(437, 40, 'Machhapuchhre'),
(438, 40, 'Madi'),
(439, 40, 'Rupa'),
(440, 41, 'Chame'),
(441, 41, 'Nar Phu'),
(442, 41, 'Nashong'),
(443, 41, 'Nesyang'),
(444, 42, 'Gharpajhong'),
(445, 42, 'Thasang'),
(446, 42, 'Dalome'),
(447, 42, 'Lomanthang'),
(448, 42, 'Barha Gaun Muktichhetra'),
(449, 43, 'Beni'),
(450, 43, 'Annapurna'),
(451, 43, 'Dhawalagiri'),
(452, 43, 'Mangala'),
(453, 43, 'Malika'),
(454, 43, 'Raghu Ganga'),
(455, 44, 'Kushma'),
(456, 44, 'Phalebas'),
(457, 44, 'Jaljala'),
(458, 44, 'Painyoo'),
(459, 44, 'Maha Shila'),
(460, 44, 'Modi'),
(461, 44, 'Bihadi'),
(462, 45, 'Baglung'),
(463, 45, 'Galkot'),
(464, 45, 'Jaimini'),
(465, 45, 'Dhorpatan'),
(466, 45, 'Bareng'),
(467, 45, 'Kathe Khola'),
(468, 45, 'Taman Khola'),
(469, 45, 'Tara Khola'),
(470, 45, 'Nisi Khola'),
(471, 45, 'Badi Gad'),
(472, 46, 'Kawasoti'),
(473, 46, 'Gaindakot'),
(474, 46, 'Devchuli'),
(475, 46, 'Bardaghat'),
(476, 46, 'Madhya Bindu'),
(477, 46, 'Ramgram'),
(478, 46, 'Sunwal'),
(479, 46, 'Susta'),
(480, 46, 'Palhi Nandan'),
(481, 46, 'Pratappur'),
(482, 46, 'Baudeekali'),
(483, 46, 'Bulingtar'),
(484, 46, 'Binayee Tribeni'),
(485, 46, 'Sarawal'),
(486, 46, 'Hupsekot'),
(487, 47, 'Musikot'),
(488, 47, 'Resunga'),
(489, 47, 'Isma'),
(490, 47, 'Kali Gandaki'),
(491, 47, 'Gulmi Durbar'),
(492, 47, 'Satyawati'),
(493, 47, 'Chandrakot'),
(494, 47, 'Ruru'),
(495, 47, 'Chhatrakot'),
(496, 47, 'Dhurkot'),
(497, 47, 'Madane'),
(498, 47, 'Malika'),
(499, 48, 'Rampur'),
(500, 48, 'Tansen'),
(501, 48, 'Nisdi'),
(502, 48, 'Purba Khola'),
(503, 48, 'Rambha'),
(504, 48, 'Matha Gadhi'),
(505, 48, 'Tinau'),
(506, 48, 'Bagnaskali'),
(507, 48, 'Ribdikot'),
(508, 48, 'Raina Devi Chhahara'),
(509, 49, 'Butwal Sub-Metropolitan'),
(510, 49, 'Devdaha'),
(511, 49, 'Lumbini Sanskritik'),
(512, 49, 'Siddharthanagar'),
(513, 49, 'Saina Maina'),
(514, 49, 'Tilottama'),
(515, 49, 'Gaidahawa'),
(516, 49, 'Kanchan'),
(517, 49, 'Kotahi Mai'),
(518, 49, 'Marchawari'),
(519, 49, 'Mayadevi'),
(520, 49, 'Om Satiya'),
(521, 49, 'Rohini'),
(522, 49, 'Sammari Mai'),
(523, 49, 'Siyari'),
(524, 49, 'Suddodhana'),
(525, 50, 'Kapilbastu'),
(526, 50, 'Buddabhumi'),
(527, 50, 'Shivaraj'),
(528, 50, 'Maharajganj'),
(529, 50, 'Krishna Nagar'),
(530, 50, 'Banganga'),
(531, 50, 'Mayadevi'),
(532, 50, 'Yashodhara'),
(533, 50, 'Suddodhana'),
(534, 50, 'Bijay Nagar'),
(535, 51, 'Sandhikharka'),
(536, 51, 'Shit Ganga'),
(537, 51, 'Bhumikasthan'),
(538, 51, 'Chhatra Dev'),
(539, 51, 'Panini'),
(540, 51, 'Malarani'),
(541, 52, 'Pyuthan'),
(542, 52, 'Swargadwari'),
(543, 52, 'Gaumukhi'),
(544, 52, 'Mandavi'),
(545, 52, 'Sarumarani'),
(546, 52, 'Mallarani'),
(547, 52, 'Nau Bahini'),
(548, 52, 'Jhimaruk'),
(549, 52, 'Eairabati'),
(550, 53, 'Rolpa'),
(551, 53, 'Triveni'),
(552, 53, 'Dui Kholi'),
(553, 53, 'Madi'),
(554, 53, 'Runti Gadhi'),
(555, 53, 'Lungri'),
(556, 53, 'Sukidaha'),
(557, 53, 'Sunchhahari'),
(558, 53, 'Subarnawati'),
(559, 53, 'Thawang'),
(560, 54, 'Musikot'),
(561, 54, 'Chaurjahari'),
(562, 54, 'Aathabiskot'),
(563, 54, 'Putha Uttar Ganga'),
(564, 54, 'Bhume'),
(565, 54, 'Sisne'),
(566, 54, 'Banfikot'),
(567, 54, 'Triveni'),
(568, 54, 'Sani Bheri'),
(569, 55, 'Tulsipur Sub-Metropolitan'),
(570, 55, 'Ghorahi Sub-Metropolitan'),
(571, 55, 'Lamahi'),
(572, 55, 'Banglachuli'),
(573, 55, 'Dangi Sharan'),
(574, 55, 'Gadhawa'),
(575, 55, 'Rajpur'),
(576, 55, 'Rapti'),
(577, 55, 'Shanti Nagar'),
(578, 55, 'Babai'),
(579, 56, 'Nepalgunj Sub-Metropolitan'),
(580, 56, 'Kohalpur'),
(581, 56, 'Narainapur'),
(582, 56, 'Raptisonari'),
(583, 56, 'Baijanath'),
(584, 56, 'Khajura'),
(585, 56, 'Duduwa'),
(586, 56, 'Janaki'),
(587, 57, 'Gulariya'),
(588, 57, 'Maduvan'),
(589, 57, 'Rajapur Taratal'),
(590, 57, 'Thakura Baba'),
(591, 57, 'Bansgadhi'),
(592, 57, 'Bar Bardiya'),
(593, 57, 'Badhaiya Tal'),
(594, 57, 'Geruwa'),
(595, 58, 'Sharada'),
(596, 58, 'Bagchaur'),
(597, 58, 'Bangad Kupinde'),
(598, 58, 'Kalimati'),
(599, 58, 'Triveni'),
(600, 58, 'Kapurkot'),
(601, 58, 'Chhatreshwori'),
(602, 58, 'Dhorchaur'),
(603, 58, 'Kumakha Malika'),
(604, 58, 'Darma'),
(605, 59, 'Birendra Nagar'),
(606, 59, 'Bheri Ganga'),
(607, 59, 'Gurbhakot'),
(608, 59, 'Panchapuri'),
(609, 59, 'Lek Besi'),
(610, 59, 'Chaukune'),
(611, 59, 'Baraha Tal'),
(612, 59, 'Chingad'),
(613, 59, 'Simta'),
(614, 60, 'Narayan'),
(615, 60, 'Dullu'),
(616, 60, 'Chamunda Bindrasaini'),
(617, 60, 'Aathabis'),
(618, 60, 'Bhagawati Mai'),
(619, 60, 'Gurans'),
(620, 60, 'Dungeshwor'),
(621, 60, 'Naumule'),
(622, 60, 'Mahabu'),
(623, 60, 'Bairavi'),
(624, 60, 'Thantikandh'),
(625, 61, 'Bheri'),
(626, 61, 'Chhedagad'),
(627, 61, 'Triveni Nalgad'),
(628, 61, 'Kuse'),
(629, 61, 'Junichande'),
(630, 61, 'Barekot'),
(631, 61, 'Shivalaya'),
(632, 62, 'Thuli Bheri'),
(633, 62, 'Tripura Sundari'),
(634, 62, 'Dolpo Buddha'),
(635, 62, 'She-Phoksundo'),
(636, 62, 'Jagadulla'),
(637, 62, 'Mudke Chula gaun'),
(638, 62, 'Kaieke'),
(639, 62, 'Chharka Tongsong'),
(640, 63, 'Chandannath'),
(641, 63, 'Kanaka Sundari'),
(642, 63, 'Sinja'),
(643, 63, 'Hima'),
(644, 63, 'Tila'),
(645, 63, 'Guthichaur'),
(646, 63, 'Tatopani'),
(647, 63, 'Patarasi'),
(648, 64, 'Khandachakra'),
(649, 64, 'Raskot'),
(650, 64, 'Tila Gupha'),
(651, 64, 'Pachal Jharana'),
(652, 64, 'Sanni Triveni'),
(653, 64, 'Narhari Nath'),
(654, 64, 'Kalika'),
(655, 64, 'Mahawai'),
(656, 64, 'Palata'),
(657, 65, 'Chhaya Nath Rara'),
(658, 65, 'Mugumakarmarong'),
(659, 65, 'Soru'),
(660, 65, 'Khatyad'),
(661, 66, 'Simkot'),
(662, 66, 'Namkha'),
(663, 66, 'Kharpu Nath'),
(664, 66, 'Sarkegad'),
(665, 66, 'Chankheli'),
(666, 66, 'Adanchuli'),
(667, 66, 'Tanjakot'),
(668, 67, 'Badi Malika'),
(669, 67, 'Triveni'),
(670, 67, 'Budhi Ganga'),
(671, 67, 'Budhi Nanda'),
(672, 67, 'Gaumul'),
(673, 67, 'Pandav Gupha'),
(674, 67, 'SwamiKartik'),
(675, 67, 'Chhededaha'),
(676, 67, 'Himali'),
(677, 68, 'Jaya Prithvi'),
(678, 68, 'Bungal'),
(679, 68, 'Talkot'),
(680, 68, 'Masta'),
(681, 68, 'Khaptadchhanna'),
(682, 68, 'Thalara'),
(683, 68, 'Bitthadchir'),
(684, 68, 'Surma'),
(685, 68, 'Chhabis Pathibhera'),
(686, 68, 'Durgathali'),
(687, 68, 'Kedarsyun'),
(688, 68, 'Kanda'),
(689, 69, 'Mangalsen'),
(690, 69, 'Sanfebagar'),
(691, 69, 'Kamalbazar'),
(692, 69, 'Panchdeval Binayak'),
(693, 69, 'Chourpati'),
(694, 69, 'Mellekh'),
(695, 69, 'Bannigadhi Jayagadh'),
(696, 69, 'RamaRoshan'),
(697, 69, 'Dhakari'),
(698, 69, 'Turmakhand'),
(699, 70, 'Dipayal-Silgadi'),
(700, 70, 'Shikhar'),
(701, 70, 'Purbi Chouki'),
(702, 70, 'Badikedar'),
(703, 70, 'Jorayal'),
(704, 70, 'Sayal'),
(705, 70, 'Aadarsha'),
(706, 70, 'K.I. Singh'),
(707, 70, 'Bogtan'),
(708, 71, 'Dhangadhi Sub-Metropolitan'),
(709, 71, 'Tikapur'),
(710, 71, 'Ghodaghodi'),
(711, 71, 'Lamki-Chuha'),
(712, 71, 'Bhajani'),
(713, 71, 'Godavari'),
(714, 71, 'Gauri Ganga'),
(715, 71, 'Janaki'),
(716, 71, 'Bardagoriya'),
(717, 71, 'Mohanyal'),
(718, 71, 'Kailari'),
(719, 71, 'Joshipur'),
(720, 71, 'Chure'),
(721, 72, 'Bhimdatta'),
(722, 72, 'Punarbas'),
(723, 72, 'Bedkot'),
(724, 72, 'Mahakali'),
(725, 72, 'Shuklaphanta'),
(726, 72, 'Belauri'),
(727, 72, 'Krishnapur'),
(728, 72, 'Beldandi'),
(729, 72, 'Laljhadi'),
(730, 73, 'Amargadhi'),
(731, 73, 'Parashuram'),
(732, 73, 'Aalitaal'),
(733, 73, 'Bhageshwor'),
(734, 73, 'Navadurga'),
(735, 73, 'Ajaymeru'),
(736, 73, 'Ganyapdhura'),
(737, 74, 'Dasharath Chanda'),
(738, 74, 'Patan'),
(739, 74, 'Melauli'),
(740, 74, 'Purchaundi'),
(741, 74, 'Surnaiya'),
(742, 74, 'Sigas'),
(743, 74, 'Shivanath'),
(744, 74, 'Pancheshwor'),
(745, 74, 'Dogada Kedar'),
(746, 74, 'Dilasaini '),
(747, 75, 'Mahakali'),
(748, 75, 'Sailya Shikhar'),
(749, 75, 'Malikarjun'),
(750, 75, 'Api Himal'),
(751, 75, 'Duhun'),
(752, 75, 'Naugad'),
(753, 75, 'Marma'),
(754, 75, 'Lekam'),
(755, 75, 'Byans'),
(756, 15, 'Rajgadh'),
(757, 17, 'Dhanauji'),
(758, 19, 'Basbariya'),
(759, 19, 'Kaudena'),
(760, 19, 'Parsa'),
(761, 20, 'Rajdevi'),
(762, 20, 'Yemunamai'),
(763, 21, 'Bishrampur'),
(764, 22, 'Thori')

SET IDENTITY_INSERT municipality_vdc OFF
END
END;
