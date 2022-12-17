-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2022 at 08:46 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlycuahangnongsan`
--
CREATE DATABASE IF NOT EXISTS `quanlycuahangnongsan` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `quanlycuahangnongsan`;

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `receiver` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `total` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`id`, `customer_id`, `receiver`, `address`, `phone`, `total`, `created_at`) VALUES
(4, 4, 'Trung', 'Vĩnh Long', '0174926492', 646000, '2022-12-15 13:52:11'),
(5, 4, 'Minh Hiếu', 'Vịnh Hạ Long', '01271239821', 88500, '2022-11-17 15:20:20'),
(6, 4, 'Trung', 'Hà Nội', '0147219831', 21000, '2022-12-15 15:29:30'),
(7, 8, 'Xuân', 'Đà Nẵng', '01412891239', 15400, '2022-12-15 15:40:52'),
(8, 8, 'Xuân', 'Tiền Giang', '02161923613', 325000, '2022-07-23 21:56:02'),
(9, 12, 'Hà', 'Sóc Trăng', '0123612983', 248600, '2022-01-07 11:58:23');

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

CREATE TABLE `bill_details` (
  `id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill_details`
--

INSERT INTO `bill_details` (`id`, `bill_id`, `product_id`, `quantity`) VALUES
(1, 4, 23, 1),
(2, 4, 25, 1),
(3, 4, 38, 2),
(4, 5, 11, 3),
(5, 6, 22, 1),
(6, 7, 31, 1),
(7, 8, 24, 1),
(8, 8, 29, 3),
(9, 8, 15, 1),
(10, 9, 39, 1),
(11, 9, 26, 1),
(12, 9, 16, 2);

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Rau'),
(2, 'Củ'),
(3, 'Quả'),
(4, 'Ngũ cốc'),
(5, 'Trái cây');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `image` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `title`, `description`, `image`, `price`, `quantity`, `category_id`) VALUES
(10, 'Khoai tây', 'Khoai tây có chứa các vitamin, khoáng chất và một loạt các hóa chất thực vật như các carotenoit và phenol tự nhiên.\r\n\r\nKhoai tây chứa khoảng 26gr carbohydrate trong một củ có kích cỡ trung bình. Đây cũng là thành phần chính trong khoai tây. Các hình thức tồn tại chủ yếu của carbohydrate này là ở dạng tinh bột.\r\n\r\nMột phần nhỏ trong tinh bột là tinh bột kháng, được coi là có lợi ích cho sức khỏe giống như chất xơ là chống ung thư ruột kết, tăng khả năng nạp glucose, giảm nồng độ cholesterol và chất béo trung tính trong huyết tương, tạo cảm giác no lâu.', '/images/cach-an-khoai-tay-tot-cho-he-tieu-hoa.jpg', 29000, 500, 2),
(11, 'Bắp cải trắng 1kg', '<p><strong>Bắp cải trắng</strong> là một loại thực phẩm vô cùng quen thuộc, rất dễ mua và chế biến thành nhiều món ăn đa dạng khác nhau trong bữa cơm hằng ngày. Tuy nhiên, về những công dụng đối với sức khỏe của nó thì không phải ai cũng biết. Bắp cải trắng đặc biệt mang đến lợi ích trong việc hỗ trợ phòng chống ung thư, giúp cơ thể khỏe mạnh toàn diện.</p><p><strong>Bắp cải trắng </strong>có nhiều lớp lá cuộn lại với nhau tạo thành khối hình cầu độc đáo. Và là loại rau củ rất quen thuộc trong các bữa cơm của gia đình Việt</p>', '/images/untitled_design__11__c5ba3c8000d44ceeaf74d09a22ba3f98_master.png', 29500, 597, 1),
(12, 'Bí đỏ tròn kg', '<p><strong>Bí đỏ tròn </strong>bùi ngậy, thơm ngon và bảo quản theo tiêu chuẩn nghiêm ngặt để giữ được độ tươi ngon của thực phẩm. Sự kết hợp giữa bí đỏ béo bùi với xương heo ngọt, canh bí đỏ xương heo luôn là sự lựa chọn tuyệt vời cho mâm cơm mỗi ngày của bạn.</p><h3><strong>Giá trị dinh dưỡng của bí đỏ</strong></h3><ul><li>Trong bí đỏ chứa nhiều chất xơ, các khoáng chất tốt cho cơ thể như kali, canxi, kẽm,... Bí đỏ còn chứa nhiều vitamin A, vitamin B,... tốt cho cơ thể.</li><li>Trong 100g bí đỏ có khoảng 26 Kcal.</li></ul><h3><strong>Tác dụng của bí đỏ đối với sức khỏe</strong></h3><ul><li>Bảo vệ thị lực tốt</li><li>Tăng cường hệ miễn dịch</li><li>Hỗ trợ giảm cân hiệu quả</li><li>Tăng cường sức khỏe tim và da</li></ul><h3><strong>Các món ăn ngon từ bí đỏ</strong></h3><ul><li>Một số món thơm ngon, hấp dẫn khi nấu với bí ngô:</li><li>Canh bí đỏ thịt bằm, canh tôm bí đỏ.</li><li>Sữa bí ngô hạt sen.</li><li>Chè bí đỏ đậu phộng đơn giản, ngon miệng.</li><li>Bí đỏ xào tỏi.</li></ul><h3><strong>Cách bảo quản bí đỏ tươi ngon</strong></h3><ul><li>Bí đỏ bảo quản trong ngăn mát tủ lạnh để giữ bí được tươi ngon, béo bùi.<br><strong>Lưu ý:&nbsp;</strong>Sản phẩm nhận được có thể khác với hình ảnh về màu sắc và số lượng nhưng vẫn đảm bảo về mặt khối lượng và chất lượng.</li></ul>', '/images/b9edb47fb13ffa61a2f24d9de633ee32_f7dc0339ec9644ae93f7eb26644592d8_master.png', 26900, 450, 3),
(13, 'Bông cải xanh 500g', '<p><strong>Bông cải xanh&nbsp;</strong>còn được gọi là Súp lơ xanh hoặc Cải bông xanh (tên tiếng Anh là Broccoli) là loại cây thuộc loài Cải bắp dại, họ Brassica oleracea có nguồn gốc từ Ý. Hiện nay, ở Việt Nam, bông cải xanh được trồng ở những vùng có khí hậu mát mẻ như Đà Lạt và một số khu vực Tây Nguyên.</p><p>Một số món ăn phổ biến, được làm từ&nbsp;<strong>bông cải xanh</strong>:</p><p>- Súp bông cải xanh</p><p>- Bông cải xanh xào thịt bò/ tôm/ thịt gà</p><p>- Bông cải xanh xào cùng các loại rau củ khác</p><p>- Salad bông cải xanh...</p>', '/images/untitled_design__6__27504f44d5fe4fc6a151c9724b174f16_master.png', 42000, 570, 1),
(14, 'Bông thiên lý 100g', '<p><strong>Bông thiên lý</strong>&nbsp;hay còn được gọi là Dạ Lý Hương, là một loại hoa có đặc điểm màu xanh phớt vàng và bông hoa rất nhỏ. Bông thiên lý thường mọc thành chùm có mùi hương dịu nhẹ. Đặc biệt, loại hoa này có thể kết hợp với nhiều nguyên liệu để chế biến các món ăn thơm ngon và hấp dẫn khác nhau. Với hương thơm nhẹ nhàng từ những cánh hoa, vị ngọt thanh của hoa, tất cả hòa quyện tạo nên một món ăn tuyệt vời.</p><p><strong>Giá trị dinh dưỡng:</strong></p><ul><li>Giúp chữa mất ngủ, ngủ ngon hơn.</li><li>Giúp giảm đau nhức xương khớp.</li><li>Tốt cho người bị vô sinh.</li><li>Giúp hỗ trợ giảm cân.</li></ul><p><strong>Hướng dẫn sử dụng:&nbsp;</strong>Bảo quản ngăn mát tủ lạnh.</p><p>&nbsp;</p><p>Các món ăn từ bông thiên lý có thể kể đến như: canh giò sống bông thiên lý, canh cua bông thiên lý, thịt bò xào bông thiên lý,...</p>', '/images/untitled_design__16__b16164cfd4804dababc2551642536897_master.png', 19000, 640, 1),
(15, 'Cải bẹ xanh 500g', '<p><strong>Cải bẹ xanh&nbsp;</strong>hay còn gọi là cải cay, cải canh,... có tên khoa học là&nbsp;Brassica juncea (L.). Tuy cùng họ cải và khá gần với nhau nhưng cải bẹ xanh có \"ngoại hình\" hoàn toàn khác với Cải ngọt với phần lá có răng cưa ở viền, mặt lá nhám và trải dọc đến tận cuốn. Cũng như nhiều loại &nbsp;khác, cải bẹ xanh chứa hàm lượng calories rất thấp nhưng lại có nhiều chất dinh dưỡng cần thiết cho cơ thể như&nbsp;<strong>Vitamin A, B, C, K, Axit nicotic, Abumin, Catoten…</strong></p>', '/images/cai-xanh-vietrat_165867ba6143480cb38134ddebb99e93_master.png', 16000, 439, 1),
(16, 'Cà rốt Đà Lạt 500g', '<p>Cà rốt được trồng đầu tiên ở Afghanistan vào khoảng năm 900 sau Công nguyên. Nhiều người biết đến cà rốt với màu cam rực rỡ đặc trưng, nhưng thực tế thì loại củ này cũng có các màu sắc khác, chẳng hạn như tím hoặc vàng, đỏ và trắng.</p><p>Loại củ phổ biến và đa năng này có thể mang hương vị hơi khác nhau tùy thuộc vào màu sắc, kích thước và nơi trồng. Đường trong cà rốt tạo ra vị ngọt nhẹ, nhưng đôi khi cũng có thể mang mùi đất hoặc hơi đắng.</p><p>Một khẩu phần nửa cốc <strong>cà rốt</strong> có:</p><ul><li>25 calo;</li><li>6 gram carbohydrate;</li><li>2 gram chất xơ;</li><li>3 gram đường;</li><li>0,5 gram protein.</li></ul>', '/images/ca-rot-da-lat-tui-500g-2-5-cu-202205201603299392_fe987cdf2793427ca82cbaf90c49a86f_master.png', 16800, 446, 2),
(17, 'Cà chua beef 500g', '<p><strong>Cà chua beef l</strong>à loại rau củ rất tốt cho sức khoẻ của người sử dụng, loại rau củ này không những giúp bổ sung chất dinh dưỡng cần thiết cho cơ thể mà chúng có giúp làm đẹp da cho phái nữ. Cà chua có thể ăn sống hoặc chế biến các món ăn cũng rất ngon.</p><p><strong>Sơ chế cà chua</strong></p><p>Cà chua mua về rửa sạch, rồi tùy theo yêu cầu món ăn mà cắt cà chua thành khối tùy thích</p><p>Món ngon từ cà chua</p><p>Cá nục kho cà chua&nbsp;thơm ngon</p><p>Trứng chiên cà chua đơn giản dễ làm</p><p>Thịt heo sốt cà chua chua chua ngọt ngọt</p><p>Nước ép cà chua cà rốt&nbsp;đẹp da sáng mắt</p><p>Canh thịt bò cà chua đậm đà</p>', '/images/ca-chua-beef-hop-500g-202101071041037134_c05a68b6faf246c48e9ce89a2d4edc5a_master.png', 26000, 519, 3),
(18, 'Cải ngọt Trường Phát gói 300g', '<p><strong>Cải ngọt&nbsp;</strong>là một trong những loại rau cải được sử dụng phổ biến trong các bữa ăn của người Việt Nam. Cải ngọt có thân tròn, phần lá có dạng tròn hoặc tù, màu xanh mướt. Cải ngọt có vị ngọt thanh, khi già thì có vị cay và nồng, rất phù hợp trong việc chế biến nhiều món ăn khác nhau. Ngoài ra cải ngọt còn mang nhiều lợi ích cho sức &nbsp;như phòng ngừa ung thư, hỗ trợ trị bệnh gout, trĩ, xơ gan, tăng sức đề kháng và thanh lọc cơ thể.&nbsp;</p><h3><strong>Các món ăn được chế biến từ cải ngọt</strong></h3><p>- Cải ngọt có vị ngọt thanh tự nhiên, vì thế chúng thường được chế biến thành các món canh để tạo ra vị thanh đạm như canh cải ngọt thịt bằm<br>- Khi kết hợp cải ngọt với các loại nấm, thịt heo, thịt bò,hải sản.. sẽ tạo ra những món xào đơn giản như rất thơm ngon và bổ dưỡng như cải ngọt xào tỏi, cải ngọt xào nấm bào ngư,...<br>- Đặc biệt, cải ngọt là một trong những nguyên liệu không thể thiếu để tạo ra những món mì xào giòn hải sản, mì xào thịt bò,... thơm ngon nứt mũi.&nbsp;</p>', '/images/untitled_design__13__ac10d9eede274d52ac3c246c87660ac5_master.png', 10500, 540, 1),
(19, 'Bí đỏ non 500g', '<p><strong>Bí đỏ non</strong>&nbsp;là một nguyên liệu phổ biến dùng để nấu ra những món ăn ngon như canh bí đỏ thịt bằm, bí đỏ hầm xương,... ngon và hấp dẫn vô cùng. <strong>Bí đỏ non </strong>mềm, ngon và bổ dưỡng vô cùng</p><p><strong>Bí đỏ non&nbsp;</strong>(hay còn gọi là&nbsp;<strong>bí ngô non</strong>) là củ&nbsp;được nhiều người yêu thích bởi&nbsp;<strong>vị ngọt nhẹ, thơm mát,</strong>&nbsp;có thể chế biến thành nhiều món như luộc, xào, nấu... Thực tế, đây là những trái bí bị sâu hay còi cọc do ảnh hưởng của thời tiết. Những trái bí này không thể lớn được nên người dân thường hái chúng lúc còn non để chế biến thành những món ăn ngon dân dã.&nbsp;</p><h3><strong>GIÁ TRỊ DINH DƯỠNG</strong></h3><p>Trong 100g bí đỏ non có chứa 26kcal, 7g Cacbohydrat, 0.5g chất xơ, 2.8g đường, cùng các loại vitamin và khoáng chất thiết yếu như Vitamin A, Vitamin C, Canxi, Vitamin B6, Magie,... Do bí đỏ non có hàm lượng beta-carotene rất cao nên tốt cho tim mạch, phát triển não bộ, tăng cường hệ miễn dịch, tốt cho sự phát triển của thai nhi, bổ mắt, phòng ngừa ung thư, tốt cho xương, ngăn ngừa loét dạ dày và tá tràng, giúp làm đẹp da, ngăn ngừa tiểu đường và hạt bí còn có tác dụng tẩy giun rất hiệu quả.</p><p>&nbsp;Một số món nấu với bí đỏ thanh mát và thơm ngon:: Canh bí đỏ nhồi thịt hoặc&nbsp;&nbsp;đơn giản với cơm gạo lứt bí đỏ..</p><h3><strong>Hướng dẫn bảo quản</strong></h3><p>Bảo quản ở nhiệt độ bảo quản 4 - 8 độ C, nên sử dụng sản phẩm trong ngày.</p>', '/images/tai_xuong_e3619441c7ae419981dddea8769239e5_master.png', 16750, 620, 3),
(20, 'Cải bó xôi T.Phát gói 300g', '<p><strong>Cải Bó Xôi – Loại rau giàu dinh dưỡng, hỗ trợ sức khỏe tuyệt vời.Trong số các loại rau cải thì Cải Bó Xôi vừa có màu sắc đẹp vừa mang đến nhiều giá trị bồi bổ cực kỳ đặc biệt.</strong></p><p>Cải Bó Xôi được đánh giá rất cao bởi tác dụng giúp hỗ trợ phòng ngừa ung thư, tốt cho mắt và tim mạch. Đồng thời, từ loại rau này chúng ta cũng có thể làm ra được rất nhiều món ăn ngon với hương vị độc, lạ khó quên.</p><p>Đặc biệt trong cải có chứa nhiều chất xơ không hòa tan giúp hỗ trợ tiêu hóa, ngăn ngừa táo bón cực kỳ tốt. Ngoài ra, các Vitamin và khoáng chất khác có trong rau có thể kể đến bao gồm:</p><ul><li>Vitamin A, B6, B9, C, E</li><li>Khoáng chất Sắt, Canxi, Kali, Magie</li></ul><p>Đồng thời còn có một số hợp chất thực vật khác rất có lợi cho cơ thể con người như:</p><ul><li>Kaempferol (Một hợp chất chống oxy hóa giúp phòng ngừa bệnh mãn tính và ung thư)</li><li>Quercetin (Hợp chất chống oxy có hiệu quả trong việc ngăn ngừa viêm nhiễm)</li><li>Lutein, Zeaxanthin (Tốt cho mắt)</li><li>Nitrate (Hỗ trợ sức khỏe tim mạch)</li></ul>', '/images/untitled_design__15__3f250b235d4442fc86bd028ee951d8bc_master.png', 20500, 450, 1),
(21, 'Cà chua 500g', '<h2>Thành phần dinh dưỡng của cà chua</h2><p>Hàm lượng nước của cà chua khoảng 95%. 5% còn lại chủ yếu bao gồm carbohydrate và chất xơ cùng nhiều <a href=\"https://suckhoedoisong.vn/cac-vitamin-va-khoang-chat-quan-trong-voi-nguoi-lon-tuoi-169210815164628925.htm\">vitamin và khoáng chất</a> khác.</p><p><i>Trong một quả cà chua sống trung bình nặng khoảng 100 gam có chứa:</i></p><p>- Lượng calo: 18</p><p>- Nước: 95%</p><p>- Chất đạm: 0,9 gam</p><p>- Carb: 3,9 gam</p><p>- Đường: 2,6 gam</p><p>- Chất xơ: 1,2 gam</p><p>- <a href=\"https://suckhoedoisong.vn/an-nhieu-chat-beo-se-thuc-day-su-phat-trien-vi-khuan-co-hai-trong-ruot-tang-nguy-co-mac-benh-tim-16921082311575732.htm\">Chất béo</a>: 0,2 gam</p><p>Cà chua là một nguồn cung cấp chất xơ dồi dào. Hầu hết các chất xơ (87%) trong cà chua là dạng không hòa tan, ở dạng hemicellulose, cellulose và lignin</p>', '/images/ca-chua-da-lat-502x502_f6f408cb4173423d94e1a51984adcf24_master.png', 20300, 540, 3),
(22, 'Củ dền 500g', '<p><strong>Củ dền </strong>là một trong những loại rau củ dinh dưỡng, mang lại rất nhiều lợi ích cho sức khỏe. Bên trong củ dền đỏ có chứa đa dạng các loại vitamin A, vitamin C, acid folic,… có công dụng thải độc của gan, điều hoà huyết áp, giảm nguy cơ nhồi máu cơ tim, giảm sự mệt mỏi, chống đột quỵ và ngăn ngừa các bệnh ung thư, chứng táo bón, vấn đề về tim mạch.</p><p>Tuy chứa nhiều dưỡng chất cần thiết cho cơ thể, nhưng không nên pha nước ép củ dền đỏ với sữa, không dùng cho người bị sỏi thận, trẻ sơ sinh dưới 6 tháng tuổi để tránh gây hại.</p><p><strong>Củ dền</strong>&nbsp;dùng để chế biến các món xào, nấu canh, làm salad và nước ép<br><strong>Bảo quản:</strong>&nbsp;ở nơi thoáng mát và khô ráo, tránh ánh nắng trực tiếp</p>', '/images/_ngon_mieng__hap_dan_nhu_muop_huong_xao_thit__muop_huong_xao_tom_g__2__06bba636acbf44baa463c9f8a18f2aa3_master.png', 21000, 604, 2),
(23, 'Táo xanh Ninh Thuận (1Kg)', '<p><strong>Thông tin dinh dưỡng:</strong></p><ul><li>Táo giúp tăng sức đề kháng&nbsp;</li><li>cho cơ thể.</li><li>Táo đặc biệt giúp cho làn da sáng bóng và săn chắc, cũng như một mái tóc óng mượt và khỏe mạnh.</li><li>Táo xanh chứa nhiều vitamin C, nếu mỗi ngày bạn ăn một quả táo thì có thể loại bỏ những nếp nhăn, nám da, có tác dụng giảm lão hóa cho làn da, giúp da mịn màng.</li><li>Vỏ táo giàu chất xơ và có lợi cho hệ tiêu hóa, hơn 1 nửa lượng vitamin C của quả táo đều nằm ở vỏ.</li><li>Sản phẩm được cấp giấy chứng nhận kiểm dịch và kiểm tra an toàn thực phẩm nhập khẩu.</li><li>Táo Xanh đặc biệt dùng để ép nước, làm salad và các món trộn,</li></ul><p><strong>Hướng dẫn sử dụng:</strong></p><ul><li>Rửa nhẹ nhàng trái táo. Sau đó cắt đôi tráitáovà loại bỏ phần hạt bên trong.</li><li>Bảo quản trong tủ lạnh từ 4 đến 8 độ C. Táo giữ được độ tươi, độ giòn trong vòng 1-4 tuần. Sau thời gian này, táo sẽ ngọt hơn, độ giòn thấp hơn và táo trở nên xốp.</li><li>Cần tránh để táo với các thực phẩm có mùi khác như hành, tỏi, táo sẽ dễ nhiễm mùi.</li><li>Không rửatrước khi cho vào tủ lạnh.</li></ul>', '/images/tao_xanh_ninh_thuan_7eba0da9f5bd4df4bc47632db908b411_grande.png', 59000, 549, 5),
(24, 'Dưa lê bạch kim Danny Green (1.3Kg - 1.5Kg/Trái )', '<p><strong>Đặc điểm nổi bật:&nbsp;</strong></p><ul><li>Bên ngoài:&nbsp;trái hình elip, vỏ vàng tươi.</li><li>Bên trong: ruột trắng, hơi giòn, vị ngọt thanh.</li></ul><p><strong>Thông tin dinh dưỡng:&nbsp;</strong></p><ul><li>Thành phần dinh dưỡng: cung cấp chất xơ, giàu vitamin C, A, B, folate...</li><li>Lợi ích: tốt cho mắt, tim mạch, phòng ngừa bệnh ung thư, tăng cường hệ miễn dịch, làm đẹp da...</li></ul>', '/images/dua_le_bach_kim_danny_green_56028b7fad744ea99b680c8c03784182_grande.png', 210000, 569, 5),
(25, 'Dâu Hàn Quốc (0.33Kg/Hộp)', '<p><strong>Đặc điểm:</strong></p><ul><li><strong>Dâu Hàn Quốc</strong>&nbsp;là loại dâu có kích thước trái lớn hơn thông thường, vị ngọt thanh tự nhiên, dễ ăn, đặc biệt dâu ửng đỏ đậm hơn sẽ có vị ngọt đậm hơn, trái còn hơi xanh sẽ có vị chua nhẹ xen kẽ.&nbsp;</li><li>Ăn nhiều dâu tây giúp thúc đẩy chuyển hóa các chất trong cơ thể, làm máu huyết lưu thông, đồng thời có tác dụng trấn tĩnh an thần và phòng chống lão hóa.</li></ul><p><strong>Thông tin dinh dưỡng:</strong></p><ul><li>Dâu tây chứa các vitamin và chất dinh dưỡng thiết yếu như, vitamin C, chất xơ, kali, vitamin B và magiê làm cho chúng hoàn hảo cho một chế độ ăn uống lành mạnh.&nbsp;</li><li>Tăng cường hệ miễn dịch: Dâu tây rất giàu vitamin C giúp cơ thể tăng cường hệ miễn dịch, đồng thời còn là chất chống oxy hóa mạnh mẽ.</li><li>Phòng chống các bệnh ung thư: Vitamin C kết hợp axit ellagic, lutein và zeathaccins có trong dâu tây giúp ngăn ngừa ung thư, chế ngự sự phát triển của các khối u, tiêu diệt các gốc tự do và trung hòa ảnh hưởng tiêu cực có khả năng xảy ra ở tế bào trong cơ thể.</li><li>Đẹp da: Vitamin C trong dâu tây có tác dụng sản sinh ra collagen, cải thiện độ đàn hồi cho da.&nbsp;</li><li>Ngoài ra, dâu tây còn giúp ổn định cân nặng. Phù hợp với những chế độ ăn kiêng dành cho người béo.</li></ul><p><strong>Hướng dẫn sử dụng:</strong></p><ul><li>Rửa nhẹ nhàng dâu tây và loại bỏ mũ lá khi bạn rửa xong.&nbsp;</li><li>Khi được bảo quản đúng cách tại nhà, dâu tây có thể tươi từ 2 - 4 ngày sau khi mua.&nbsp;</li><li>Giữ lạnh dâu tây giúp bảo quản lâu hơn.&nbsp;</li><li>Chúng tôi giữ các loại quả mọng trong tủ lạnh và đặc biệt khuyên bạn nên giữ chúng trong hộp đựng ban đầu và làm lạnh chúng càng sớm càng tốt.&nbsp;</li><li>Không rửa trước khi cho vào tủ lạnh.</li></ul><p><br>&nbsp;</p>', '/images/1_ece91f8e97cd40fb94a992665a3899b9_1024x1024.png', 299000, 639, 5),
(26, 'Hạnh nhân rang bơ túi 200g', '<p><strong>Hạnh nhân rang bơ</strong> là loại thực phẩm chứa nhiều dinh dưỡng, dùng nhiều để làm món ăn vặt hoặc làm bánh, sữa hạnh nhân và một số món ăn khác. Ở Việt Nam, <strong>cây hạnh nhân</strong> được trồng ở nhiều nơi, nhất là vùng Bắc Trung Bộ như Thanh Hóa, Nghệ An, Hà Tĩnh,....</p><p><strong>Hạnh nhân</strong> thuộc cây thân gỗ với chiều cao phát triển lên đến 10m. Quả hạnh nhân cứng, kích thước to hơn quả mơ và được bao phủ một lớp da dày màu xám bên ngoài. Bên trong chính là hạt hạnh nhân.&nbsp;</p>', '/images/hanh-nhan-rang-bo3.png', 65000, 439, 4),
(27, 'Hạt điều bể nhân sống trắng 250gr', '<p><strong>Hạt điều bể nhân sống trắng</strong> hay còn được gọi là hạt điều nhân trắng bể đôi là loại hạt điều có màu trắng, và còn sống, nguyên sơ chưa qua chế biến, chỉ mới được sơ chế tách bỏ lớp vỏ bên ngoài và loại bỏ đi lớp vỏ lụa. Để lại những nhân hạt trắng, bóng mẩy bên trong. Tuy nhiên trong quá trình chế biến đã làm hạt bị vỡ đôi theo chiều dọc từ trên xuống dưới. Tên gọi hạt điều bể nhân sống trắng được ra đời là do vậy.</p>', '/images/hat-dieu-be-nhan-song-trang1.png', 60000, 540, 4),
(28, 'Đậu Hà Lan khô 500gr', '<p><strong>Đậu Hà Lan</strong><a href=\"https://nongsandungha.com/thuc-pham/dau-ha-lan\"> </a>hay còn có tên gọi khác là đậu hòa lan, đậu pơ tí poa,... Loại đậu này có tên khoa học là Pisum sativum L, là một loại cây thuộc họ đậu, bộ phận dùng là quả tươi và hạt.&nbsp;</p><p><strong>Đậu hà Lan khô</strong> là thành phẩm sau khi đã được sơ chế, có thể bảo quản và sử dụng như đậu hà lan tươi, bên cạnh đó đậu Hà Lan khô còn có thể chế biến thành rất nhiều <a href=\"http://kinggroup.info/mon-an-tu-bap-cai.html\">món ăn</a> khác rất bổ dưỡng.&nbsp;</p>', '/images/dau-ha-lan-nguyen-hat.jpg', 50000, 440, 4),
(29, 'Bí đao kg', '<p><strong>Bí đao</strong>&nbsp;(bí xanh)&nbsp;có vị ngọt mát thường được nấu chung với tôm khô tạo thành món canh ngon quen thuộc vào mùa hè. Trong mình bí xanh có chứa rất nhiều các chất như đường, protit, vitamin E, PP, B1, B2, C đồng thời còn có sắt bổ máu, canxi bổ xương,… Sử dụng bí xanh như một loại mặt nạ sẽ cực kỳ hữu dụng trong việc nâng tông độ sáng của làn da, tăng cường độ ẩm giúp da mềm mại.<br>Bí đao chứa nhiều nước. Nghiên cứu cho thấy, trong 100g bí đao có 0,3g protein, 1,5g glucoza, ngoài ra còn có các nguyên tố vi lượng như canxi, phốt pho, magiê, sắt và các loại vitamin B1, B2 và vitamin C. Bí đao có nhiều thành phần nhưng lại cho năng lượng calo thấp.</p><p><strong>Những công dụng nổi bật của bí đao như giảm cân, chống béo phì</strong></p><p>Có thể nhắc đến đầu tiên là tác dụng giảm cân của bí đao. Bí đao có công dụng giảm cân chủ yếu là bởi vì bí đao có khả năng làm no bụng mà không chứa năng lượng nhiều.</p><p>Trong bí đao chứa rất nhiều nước và không chứa chất béo. Hơn nữa, trong bí đao còn chứa hợp chất hóa học hyterin-caperin ngăn không cho đường chuyển hóa thành mỡ trong cơ thể, nên cơ thể sẽ không bị tích lũy mỡ thừa.</p><p>Ngoài ra, các vitamin B9, vitamin C, vitamin E, vitamin A và các khoáng chất như: kali, phospho, magiê có trong bí đao cũng góp phần làm đẹp cơ thể, loại bỏ mỡ ở bụng. Vỏ bí đao được cho là chứa nhiều vitamin và chất khoáng, nên có thể ăn cả vỏ, nhất là vỏ bí đao khi quả còn non. Với những người mắc bệnh béo phì hoặc muốn giảm cân, ăn bí đao thường xuyên thực sự rất tốt</p><p>Bạn cũng có thể dùng nước ép bôi lên da mặt hoặc trộn lẫn nước ép bí đao với nước ép dưa chuột hoặc mật ong bôi lên da mặt, khoảng 30 phút sau rửa lại bằng nước sạch. Trong Bí đao chứa rất nhiều nước và vitamin tốt cho làn da, làm thay đổi độ ẩm của da, làm mịn da, làm mờ vết nám và vết thâm do mụn,…</p><p>Đặc biệt nếu như da bạn bị nám, đen thì sau khi ép bí lấy nước, bạn nên để lại một chút nước ép rồi trộn với mật ong, đắp lên mặt, các vết nám trên mặt sẽ mờ dần đi.</p><p><strong>Các tác dụng khác</strong>: Các nghiên cứu cho thấy hàm lượng natri trong bí đao rất thấp nên có tác dụng trị liệu cho những người mắc các chứng bệnh như: Xơ cứng động mạch, tiểu đường, bệnh động mạch vành tim, viêm thận, phù thũng, bệnh cao huyết áp và béo phì.</p>', '/images/tai_xuong__26__0a3a4a8d10a945779a85782e122993fd_master.png', 33000, 547, 3),
(30, 'Củ sen tươi Sen đại việt 500g', '<p><strong>Củ sen tươi</strong>&nbsp;là nguyên liệu chế biến nên nhiều món ngon. Với hàm lượng tinh bột cao, chất xơ dồi dào, cùng với nhiều khoáng chất. Củ sen thích hợp để chế biến nhiều món ăn và có thể ăn sống mà vẫn giữ được hàm lượng dinh dưỡng.</p><p>Với các món canh hay chè, người dùng có thể sử dụng củ sen có mặt cắt 7 lỗ. những củ sen này có hàm lượng nước ít, mềm và dẻo. Các món canh thường được chế biến: canh củ sen sườn non, canh củ sen đuôi heo, canh củ sen đậu hủ …</p><p>&nbsp;Với các món xào, kho hay salad dùng để ăn sống, người dùng có thể sử dụng củ sen mặt cắt 9 lỗ. Vì những củ sen này có hàm lượng nước cao hơn, cũng như là độ giòn của củ sen. Những món ăn quen thuộc như: salad củ sen, củ sen xào nấm, củ sen kho thịt …</p><p>&nbsp;Thừa kế những công dụng tuyệt vời từ củ sen, Trà củ sen được chế biến từ củ sen tươi phơi/ sấy khô cũng mang đến rất nhiều lợi ích cho sức khỏe</p>', '/images/tai_xuong__15__d18fcc7924854f3280523428ebefe86c_master.png', 34000, 550, 2),
(31, 'Khổ qua trái 500g', '<p><strong>Khổ qua hay còn gọi là mướp đắng, thuộc họ bầu bí được ưa chuộng và trồng phổ biến tại Việt Nam</strong>. Chúng có hình thoi với hình dáng bên ngoài có nhiều u lồi khi sống có màu xanh và chuyển sang màu vàng đỏ như đào khi chín. Trong các nghiên cứu y học hiện đại, các nhà khoa học nhận thấy rằng khổ qua có chứa rất nhiều sinh tố và khoáng chất tốt cho sức khỏe như protein, lipit, cacbon hydrat, canxi, kali, magie, sắt… còn theo Đông y, mướp đắng vị đắng, lạnh và có tác dụng thanh nhiệt, giải độc.</p>', '/images/large-kho-qua-1617350110_7f576a3649834591a98054f2b892395f_master.png', 15400, 459, 3),
(32, 'Khoai mỡ kg', '<p><strong>Khoai mỡ kg</strong></p><p>Khoai mỡ là loại khoai thuộc họ dây leo với nhiều củ có tên khoa học là Dioscorea alata. Loại khoai này thường được trồng nhiều ở Châu Phi,&nbsp; Ấn Độ và Malaysia.&nbsp;</p><p>Về kích thước, khoai mỡ thường có hình dáng bên ngoài to hơn khoai lang. Khoai có màu nâu đen và lớp vỏ bên ngoài thường xù xì, nhiều rễ.&nbsp;Bên trong ruột khoai mỡ có màu tím đặc trưng. Một số loại sẽ có màu tím nhạt cho đến màu trắng được gọi là khoai mỡ trắng.</p><h3><strong>Giá trị dinh dưỡng trong 100g khoai mỡ</strong></h3><p>Khoai mỡ là giống khoai có nguồn năng lượng, dinh dưỡng dồi dào. Trong 100g khoai mỡ có chứa các chất dinh dưỡng:</p><ul><li>120 kcal Năng lượng</li><li>27gr Carbohydat</li><li>4gr chất xơ</li><li>20mg Canxi</li><li>0.36mg Sắt</li><li>100 IU Vitamin A</li></ul><p>Ngoài những chất dinh dưỡng trên là chủ yếu, trong khoai mỡ còn có: đạm, natri, sắt, axit béo và không chứa cholesterol.</p>', '/images/untitled_design__19__fef0d3fb55c54a58bd3973cbb665586a_master.png', 63000, 570, 2),
(34, 'Quả óc chó đỏ 500gr', '<p>Quả óc chó được bán trên thị trường cũng rất đa dạng và phong phú. Óc chó hiện là một trong số các loại <strong>hạt dinh dưỡng</strong> được yêu thích nhất. Hiện nay có 4 loại óc chó để bạn chọn lựa đó là óc chó đỏ, óc chó vàng,&nbsp;</p><p>Nhưng ở đây mình sẽ nói đến loại óc chó đỏ vì óc chó đỏ có hương vị ngon và thơm hơn cá loại óc chó khác. Chính vì điều này nên giá thành của quả óc chó đỏ sẽ cao hơn những loại óc chó khác một chút.</p>', '/images/oc-cho-3.jpg', 155000, 607, 4),
(35, 'Táo Envy New Zealand size 70', '<p><strong>Đặc điểm nổi bật:</strong></p><ul><li>quả to tròn, với vỏ màu đỏ điểm thêm các sọc màu vàng</li><li>thịt giòn, vị ngọt đậm, mùi thơm dịu nhẹ</li></ul><p><strong>Thông tin&nbsp;dinh dưỡng:</strong></p><ul><li>Táo là nguồn cung cấp vitamin C tuyệt vời, có tác dụng tăng cường hệ thống miễn dịch.&nbsp;Mỗi quả táo chứa khoảng 8mg vitamin này, vì thế chúng sẽ cung cấp khoảng 14% nhu cầu vitamin C hàng ngày của cơ thể.</li><li>Cũng giống như quả lê và quả việt quất, táo có mối liên hệ với việc giảm thiểu nguy cơ mắc bệnh tiểu đường type 2 nhờ chất chống oxy hóa có tên Anthocyanins, hơn nữa trong táo có chất Triterpenoids có khả năng chống lại các bệnh ung thư gan, ruột kết và ung thư vú.</li><li>Một quả táo cỡ trung bình chứa khoảng 4g chất xơ. Một phần trong số chất xơ đó ở dạng Pectin - loại chất xơ hòa tan có tác dụng giảm lượng cholesterol \"\"xấu\"\"</li><li>Chất xơ phức tạp của táo giúp no lâu hơn mà không bị tiêu thụ nhiều calo (một quả táo bình thường chỉ chứa khoảng 95 calo).</li><li>Một loại axit có trong vỏ táo là Axit Ursolic làm giảm nguy cơ béo phì, Axit Ursolic thúc đẩy cơ thể đốt cháy calo, tăng việc hình thành cơ và giảm chất béo lâu năm trong cơ thể.</li></ul>', '/images/envy_size_24_ea10166ebff94dcba425a1fa1d583c6a_1024x1024.png', 189000, 490, 5),
(36, 'Bí ngòi xanh kg', '<ul><li><strong>Bí Ngòi Xanh&nbsp;</strong>hay còn gọi là (bí ngồi, bí Nhật Bản) được trồng phổ biến ở Đà Lạt.</li><li>Quả bí dài (25-30cm), thon và trơn láng, màu xanh đậm, hình dáng khá giống quả dưa leo Nhật</li><li>Thịt dày, hạt mềm, màu trắng, nấu được nhiều món ăn ngon, bổ dưỡng</li><li>Tác dụng của Bí ngòi xanh :&nbsp;Giúp kiểm soát cân nặng,ngừa ung thư,Tăng cường sức khỏe tim, mạch...<strong>Các món ngon từ bí ngòi :&nbsp;</strong>Bí ngòi nấu canh tôm,Canh bí ngòi thịt heo,Bí ngòi xào tỏi...</li></ul>', '/images/untitled_design__18__c551aa32ff8b442bbe3d1437520ff5f1_master.png', 33500, 150, 3),
(37, 'Xoài cát Hòa Lộc (1Kg)', '<p><strong>Xoài Cát Hoà Lộc </strong>nổi tiếng là một loại xoài cơm vàng, ngọt đậm với vị thơm đặc trưng. Xoài cát ít tạo cảm giác xơ nên ăn rất ngon. Đặc biệt, xoài trồng theo tiêu chuẩn Vietgap, đảm bảo đem đến nguồn trái cây sạch và chất lượng tuyệt vời khi đến tay người tiêu dùng.</p>', '/images/xoai_cat_hoa_loc__1kg__8ec1438e598b47cca6952518e8245bbd_grande.png', 129000, 208, 5),
(38, 'Dưa hấu đỏ không hạt (3.5Kg - 4Kg/Trái)', '<p><strong>Dưa Hấu Đỏ Không Hạt</strong>&nbsp;thuộc giống dưa không hạt, mọng nước hơn và ngọt đậm. Dưa được trồng và thu hoạch đạt tiêu chuẩn an toàn thực phẩm, đảm bảo cho người tiêu dùng.&nbsp;</p><p><strong>Hướng dẫn sử dụng:</strong></p><ul><li>Dưa hấu có thể ăn trực tiếp, làm nước ép, xay sinh tố.</li><li>Dưa hấu ngon hơn khi ướp lạnh trước khi ăn.</li></ul>', '/images/dua_hau_do_khong_hat__3kg_trai__b65fa5867a6544c99edbc9546bbe479f_grande.png', 144000, 208, 5),
(39, 'Hạt bí xanh Singapore đã rang 500g', '<p>Thành phần dinh dưỡng <strong>hạt bí xanh</strong> có chứa nhiều loại khoáng chất và vitamin cần thiết cho cơ thể con người như protein, magie, mangan, đồng, kẽm,… Bên cạnh đó, hạt bí cũng chứa các hợp chất thực vật (phytosterols) và các chất chống oxy hoá giúp ngăn ngừa, loại bỏ các gốc tự do, tăng cường sức khỏe cho cơ thể. Trong hạt bí xanh có lượng chất xơ cao có lợi cho sức khỏe.</p><p>Không chỉ thế, <strong>thành phần dinh dưỡng hạt bí xanh</strong> còn chứa rất nhiều dưỡng chất quan trọng đối với cơ thể mà các thực phẩm, hạt khác không đáp ứng được như vitamin A, B1, B2, vitamin C, Niacin, canxi, photpho và sắt, chất béo, protein, carbohydrate… và cơ thể rất dễ hấp thu.</p>', '/images/dscf0905_c078e6d851f7412a847132996b17b526_5ce25027f34847e086c2ac3f00588d62_master.png', 150000, 209, 4);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `description`) VALUES
(1, 'Khách hàng'),
(2, 'Quản lý'),
(3, 'Nhân viên kho'),
(4, 'Nhân viên kế toán');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fullname`, `email`, `password`, `address`, `phone`, `role_id`) VALUES
(1, 'Nguyễn Thành Lộc', 'admin@gmail.com', '$2a$10$rU6oXrkWQVD9Na9MIx.ncew.GbYUskmCcshw/E5E9RXZMpC829162', '23, Phan Xích Long, Phường 2, Phú Nhuận', '0123456789', 2),
(2, 'Phạm Trung Tín', 'kho@gmail.com', '$2a$10$rU6oXrkWQVD9Na9MIx.ncew.GbYUskmCcshw/E5E9RXZMpC829162', '53, Nguyễn Hữu Thọ, Phường 3, Quận 7', '0174965275', 3),
(3, 'Lê Yến Nhi', 'ketoan@gmail.com', '$2a$10$rU6oXrkWQVD9Na9MIx.ncew.GbYUskmCcshw/E5E9RXZMpC829162', '74, Phạm Văn Đồng, Phường 9, Gò Vấp', '0528461903', 4),
(4, 'Mã Văn Trung', 'trung@gmail.com', '$2a$10$rU6oXrkWQVD9Na9MIx.ncew.GbYUskmCcshw/E5E9RXZMpC829162', 'Thành phố HCM', '0123592451', 1),
(7, 'Nguyễn Thanh Tuấn', 'tuan@gmail.com', '$2a$10$t7/UIZ0ZO.9/Eawb6eMUgek/NUMaJ4RMUVRnR6Z1hQWoxXshc4pVS', 'Sóc Trăng', '0123456789', 1),
(8, 'Nguyễn Mùa Xuân', 'xuan@gmail.com', '$2a$10$sLG1LpGJ94voJe1LCE0iJeRbthsAG3d0CmMpqh6LRN1aiYunk6C9S', 'Hà Nội', '0123215124124', 1),
(12, 'Nguyễn Thái Hà', 'ha@gmail.com', '$2a$10$nWk7ttDIKPDaRWiXxa58q.0VAkvoLab90LUhndUdgziJJ2Oyunncq', 'Tiền Giang', '0212135753', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_id` (`bill_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `bill_details`
--
ALTER TABLE `bill_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD CONSTRAINT `bill_details_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`),
  ADD CONSTRAINT `bill_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
