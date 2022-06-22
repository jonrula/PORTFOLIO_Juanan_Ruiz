//
//  Silabarios.swift
//  KanaCards
//
//  Created by Ion Jaureguialzo Sarasola on 26/09/2020.
//

import Foundation

// REF: Kana chart: https://en.wikibooks.org/wiki/Japanese/Kana_chart
// REF: Silabarios: https://mohayonao.hatenadiary.org/entry/20091129/1259505966

let kata = [
    "ア": "あ", "イ": "い", "ウ": "う", "エ": "え", "オ": "お",
    "カ": "か", "キ": "き", "ク": "く", "ケ": "け", "コ": "こ",
    "サ": "さ", "シ": "し", "ス": "す", "セ": "せ", "ソ": "そ",
    "タ": "た", "チ": "ち", "ツ": "つ", "テ": "て", "ト": "と",
    "ナ": "な", "ニ": "に", "ヌ": "ぬ", "ネ": "ね", "ノ": "の",
    "ハ": "は", "ヒ": "ひ", "フ": "ふ", "ヘ": "へ", "ホ": "ほ",
    "マ": "ま", "ミ": "み", "ム": "む", "メ": "め", "モ": "も",
    "ヤ": "や", "ユ": "ゆ", "ヨ": "よ",
    "ラ": "ら", "リ": "り", "ル": "る", "レ": "れ", "ロ": "ろ",
    "ワ": "わ", "ヲ": "を",
    "ン": "ん",
]

let master = [
    "a": "ア", "i": "イ", "u": "ウ", "e": "エ", "o": "オ",
    "ka": "カ", "ki": "キ", "ku": "ク", "ke": "ケ", "ko": "コ",
    "sa": "サ", "shi": "シ", "su": "ス", "se": "セ", "so": "ソ",
    "ta": "タ", "chi": "チ", "tsu": "ツ", "te": "テ", "to": "ト",
    "na": "ナ", "ni": "ニ", "nu": "ヌ", "ne": "ネ", "no": "ノ",
    "ha": "ハ", "hi": "ヒ", "fu": "フ", "he": "ヘ", "ho": "ホ",
    "ma": "マ", "mi": "ミ", "mu": "ム", "me": "メ", "mo": "モ",
    "ya": "ヤ", "yu": "ユ", "yo": "ヨ",
    "ra": "ラ", "ri": "リ", "ru": "ル", "re": "レ", "ro": "ロ",
    "wa": "ワ", "wo": "ヲ",
    "n": "ン"
]

enum Silabario {
    case hiragana
    case katakana
}

/**
 Dada una clave en romaji, devuelve su kana correspondiente en el silabario solicitado.
 */
func convertir(romaji: String, _ silabario: Silabario) -> String {
    switch silabario {
    case .katakana:
        return master[romaji] ?? "?"
    case .hiragana:
        return kata[master[romaji] ?? "?"] ?? "?"
    }
}

/**
 Devuelve un array de n claves aleatorias en romaji.
 */
func romaji(aleatorios: Int) -> [String] {

    var romaji = [String]()

    var temp = master

    for _ in 1...aleatorios {
        let aleatorio = temp.randomElement()!.key
        romaji.append(aleatorio)
        temp[aleatorio] = nil
    }

    return romaji
}
