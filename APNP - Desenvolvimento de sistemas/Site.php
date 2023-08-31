<!DOCTYPE html>
<html>
<head>
    <title>Estoque</title>
</head>
<body>
    <h1>Itens em Estoque</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome do Item</th>
            <th>Quantidade em Estoque</th>
        </tr>
        <?php
        $servername = "localhost";
        $username = "seu_usuario";
        $password = "sua_senha";
        $dbname = "estoque";

        $conn = new mysqli($servername, $username, $password, $dbname);

        if ($conn->connect_error) {
            die("Erro na conexão: " . $conn->connect_error);
        }

        $sql = "SELECT * FROM tb_item";
        $result = $conn->query($sql);

        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                echo "<tr><td>" . $row["id_item"] . "</td><td>" . $row["nm_item"] . "</td><td>" . $row["qtd_estoque"] . "</td></tr>";
            }
        } else {
            echo "Nenhum item encontrado.";
        }
        $conn->close();
        ?>
    </table>
</body>
</html>
