<html>
<head>
    <style>
        body {
            background-color: #DCDCDC;
            height: 100%;
            margin: 0 !important;
            padding: 0 !important;
            font-family: "Century Gothic", CenturyGothic, AppleGothic, sans-serif;
        }

        #content {
            width: 50%;
            margin: 0 auto;
            text-align: center;
            background-color: #F4F4F4;
            border: 5px solid black;
        }

        #container {
            margin-top: 50px;
        }

        #navbar {
            border-bottom: 4px solid black;
            border-top: 4px solid black;
            background-color: #568777;
        }

        ul {
            padding: 0;
        }

        li {
            padding-left: 5%;
            padding-right: 5%;
            display: inline;
            width: 100px;
            height: 100%;
        }

        .column {
            float: left;
            width: 50%;
        }

        .row:after {
            content: "";
            display: table;
            clear: both;
        }

        .left {
            width: 70%;
        }

        .right {
            width: 30%;
        }

        #updates {
            display: flex;
            justify-content: center;
        }

        #updatesGrid {
            width: 100%;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }

        .update {
            border: 4px solid #101010;
            width: 750px;
            height: 100px;
            margin: 10px;
            background-color: #568777;
            position: relative;
        }

        .updateHeader {
            font-weight: bold;
            padding: 5px;
            border-bottom: 4px solid black;
            height: 20px;
            line-height: 20px;
        }

        .updateContent {
            padding: 10px;
        }

        .updateFooter {
            border-top: 4px solid black;
            position: absolute;
            bottom: 0;
            width: 100%;
        }

        @media (max-width: 992px) {
            #content {
                width: 80%;
            }

            .update {
                height: 130px
            }
        }

        .commit {
            background-color: #568777;
            text-align: center;
            border: 4px solid black;
            margin: 5px;
        }

        .commitTime {
            border-top: 4px solid black;
        }
    </style>
</head>
<body>
<div id="container">
    <div id="content">
        <div id="logoHeader">
            <h1>Arrow Drift</h1>
        </div>
        <div id="navbar">
            <ul>
                <li><a href="#">Home</a></li>
                <li>About</li>
                <li>Download</li>
            </ul>
        </div>
        <div id="mainContent">
            <div class="row">
                <div class="column left">
                    <h3>Recent Updates:</h3>
                    <div id="updates">
                        <div id="updatesGrid">
                            <div class="update">
                                <div class="updateHeader">25.04.2018</div>
                                <div class="updateContent">Welcome to the Arrow Drift Website! More information coming
                                    soon.
                                </div>
                                <div class="updateFooter">Tags: Announcement, Update</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="column right">
                    <a href="https://github.com/JPatrickDev/ArrowDrift-Remake"><h3>Recent Commits:</h3>
                    </a>
                    <div id="commitArea">
                        <?php
                        $context = stream_context_create([
                            'http' => [
                                'method' => 'GET',
                                'header' => [
                                    'User-Agent: PHP'
                                ]
                            ]
                        ]);
                        $content = file_get_contents('https://api.github.com/repos/JPatrickDev/ArrowDrift-Remake/commits', false, $context);
                        $obj = json_decode($content, true);
                        $i = 0;
                        foreach ($obj as $commit) {
                            $i++;
                            if ($i > 5)
                                break;
                            ?>
                            <div class="commit">
                                <div class="commitMessage">
                                    <?= $commit['commit']['message']; ?>
                                </div>
                                <div class="commitTime">
                                    <?php
                                    $d = new DateTime($commit['commit']['committer']['date']);
                                    echo $d->format("d-m-Y");
                                    ?>
                                </div>
                            </div>

                            <?php
                        }
                        ?>

                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>