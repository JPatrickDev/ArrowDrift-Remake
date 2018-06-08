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
            padding-bottom: 40px;
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
                                <div class="updateHeader">08.06.2018</div>
                                <div class="updateContent">
                                    <br>
                                    Current version is now v0.5. Game is rapidly approaching a releasable state, with
                                    v0.5 introducing much better scaling support for both portrait and landscape
                                    resolutions.
                                    The Level Editor is now a map pack editor, allowing support for the new zip based
                                    level storage system. Map Packs can be placed in Home/Arrow Drift Data/Levels and
                                    can be loaded via the main menu.
                                </div>
                                <div class="updateFooter">Tags: Announcement, Update,Version</div>
                            </div>
                            <div class="update">
                                <div class="updateHeader">3.06.2018</div>
                                <div class="updateContent">
                                    <h3>About</h3>
                                    Arrow Drift is a re-make of one of my Ludum Dare entries. The original was made
                                    entirely in 48-hours and can be found <a
                                        href=http://ludumdare.com/compo/ludum-dare-34/?action=preview&uid=14696”>Here</a>
                                    . This project is an attempt to make a much better version of the game, with
                                    improves both internally and externally.
                                    <br>
                                    <br>
                                    Internally, the game has moved over from Slick2D/LWJGL, which has always been my
                                    preferred LD combo due to how familiar I am with it, to LibGDX. This is a library I
                                    have wanted to learn more about for a while now, so this project felt like the
                                    perfect time to do so. I’m particularly excited about the opportunity to deploy
                                    Arrow Drift on Android devices. The game has also been built on a robust tile-based
                                    game-engine, making adding new tiles/entities and levels very easy.
                                    <br>
                                    <br>
                                    Externally, it’s currently very much a Work-In-Progress. It does feature a Level
                                    Editor to easily create new levels, but there’s currently no easy way to add them to
                                    the game once it’s compiled. Work is currently in progress to convert the game to
                                    use a zip based storage system for levels to allow for easily loadable "map packs".
                                    The game runs smoothly, with animated tile support, category and level select
                                    screens, and end of category screens. The GUI looks very basic at the moment because
                                    it’s pretty much just default LibGDX skins for GUI elements. Support for scaling is
                                    lacking in places, but is improving – Hopefully an Android build will be available
                                    soon.
                                    <br>
                                    <br>
                                    Feel free to take a look at the GitHub Repo, where the source for both the game and
                                    this website can be found. Pull request are always welcome, as is general feedback.
                                    You can follow me on Twitter at @jdp30_, or email me at jack@jdp30.com.
                                    <br>
                                    <br>
                                    More updates coming soon.
                                </div>
                                <div class="updateFooter">Tags: Announcement, Update</div>
                            </div>
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
                            if ($i > 12)
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