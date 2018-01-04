with import <nixpkgs> {};
stdenv.mkDerivation {
    name = "alexandrie-dev";
    buildInputs = [
            openjdk9
            nodejs
            gradle
    ];
}
